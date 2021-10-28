/*
 * Copyright 2018-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.test.demarcationqueue.lib;

import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.stream.Task;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Predicate;


class StreamPollTask<K, V> implements Task {

    private final MessageListener<V> listener;
    private final ErrorHandler errorHandler;
    private final Predicate<Throwable> cancelSubscriptionOnError;
    private final Function<ReadOffset, Optional<CallBackData<V>>> readFunction;
    private final Duration pollTimeout ;

    private final PollState pollState;

    private volatile boolean isInEventLoop = false;

    StreamPollTask(QueueReadOptions<K> streamRequest, MessageListener<V> listener, ErrorHandler errorHandler,
                   Function<ReadOffset, Optional<CallBackData<V>>> readFunction,Duration pollTimeout) {

        this.listener = listener;
        this.errorHandler = Optional.ofNullable(streamRequest.getErrorHandler()).orElse(errorHandler);
        this.cancelSubscriptionOnError = streamRequest.getCancelSubscriptionOnError();
        this.readFunction = readFunction;
        this.pollState = PollState.standalone();
        this.pollTimeout = pollTimeout;
    }

    @Override
    public void cancel() throws DataAccessResourceFailureException {
        this.pollState.cancel();
    }

    @Override
    public State getState() {
        return pollState.getState();
    }

    @Override
    public boolean awaitStart(Duration timeout) throws InterruptedException {
        return pollState.awaitStart(timeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    @Override
    public boolean isLongLived() {
        return true;
    }

    @Override
    public void run() {
        pollState.starting();
        try {

            isInEventLoop = true;
            pollState.running();
            doLoop();
        } finally {
            isInEventLoop = false;
        }
    }

    private void doLoop() {
        do {
            try {
                // allow interruption
                Thread.sleep(0);

                listener.preRead();
                Optional<CallBackData<V>> raw = readRecords();
                raw.ifPresent(this::deserializeAndEmitRecords);
                if(!raw.isPresent()){
                   sleep();
                }
            } catch (InterruptedException e) {

                cancel();
                Thread.currentThread().interrupt();
            } catch (RuntimeException e) {

                if (cancelSubscriptionOnError.test(e)) {
                    cancel();
                }

                errorHandler.handleError(e);
            }
        } while (pollState.isSubscriptionActive());
    }

    protected void sleep() {
        try {
            TimeUnit.SECONDS.sleep(pollTimeout.getSeconds());
        } catch (InterruptedException e) {
            errorHandler.handleError(e);
        }
    }

    private Optional<CallBackData<V>> readRecords() {
        return readFunction.apply(ReadOffset.latest());
    }

    private void deserializeAndEmitRecords(CallBackData<V> records) {
        try {
            pollState.updateReadOffset();
            listener.onMessage(records);
        } catch (RuntimeException e) {

            if (cancelSubscriptionOnError.test(e)) {

                cancel();
                errorHandler.handleError(e);

                return;
            }

            errorHandler.handleError(e);
        }
    }

    @Override
    public boolean isActive() {
        return State.RUNNING.equals(getState()) || isInEventLoop;
    }

    static class PollState {
        private volatile State state = State.CREATED;
        private volatile CountDownLatch awaitStart = new CountDownLatch(1);

        private PollState() {
        }

        static PollState standalone() {
            return new PollState();
        }

        boolean awaitStart(long timeout, TimeUnit unit) throws InterruptedException {
            return awaitStart.await(timeout, unit);
        }

        public State getState() {
            return state;
        }

        boolean isSubscriptionActive() {
            return state == State.STARTING || state == State.RUNNING;
        }

        void starting() {
            state = State.STARTING;
        }

        void running() {

            state = State.RUNNING;

            CountDownLatch awaitStart = this.awaitStart;

            if (awaitStart.getCount() == 1) {
                awaitStart.countDown();
            }
        }

        void cancel() {

            awaitStart = new CountDownLatch(1);
            state = State.CANCELLED;
        }

        void updateReadOffset() {
        }
    }
}
