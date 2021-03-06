/*
 * Copyright 2011-2021 the original author or authors.
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

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.stream.Cancelable;
import org.springframework.data.redis.stream.Subscription;
import org.springframework.data.redis.stream.Task;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ErrorHandler;
import org.springframework.util.ObjectUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.test.demarcationqueue.lib.LuaScriptConst.*;

@Slf4j
public class DefaultQueueMessageListenerContainer<K, V> implements QueueMessageListenerContainer<K, V> {

    private final Object lifecycleMonitor = new Object();

    //    private final Executor taskExecutor;
//    private final ErrorHandler errorHandler;
    private final RedisTemplate<K, V> template;
    private final QueueContainerOptions<K, V> containerOptions;

    private final List<Subscription> subscriptions = new ArrayList<>();

    private boolean running = false;

    DefaultQueueMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                         QueueContainerOptions<K, V> containerOptions) {

        Assert.notNull(connectionFactory, "RedisConnectionFactory must not be null!");
        Assert.notNull(containerOptions, "StreamMessageListenerContainerOptions must not be null!");

//        this.taskExecutor = containerOptions.getExecutor();
//        this.errorHandler = containerOptions.getErrorHandler();
        this.template = createRedisTemplate(connectionFactory, containerOptions);
        this.containerOptions = containerOptions;
    }

    private RedisTemplate<K, V> createRedisTemplate(RedisConnectionFactory connectionFactory,
                                                    QueueContainerOptions<K, V> containerOptions) {

        RedisTemplate<K, V> template = new RedisTemplate<>();
        template.setKeySerializer(containerOptions.getKeySerializer());
        template.setValueSerializer(containerOptions.getValueSerializer());
        template.setConnectionFactory(connectionFactory);
        template.afterPropertiesSet();

        return template;
    }

    @Override
    public boolean isAutoStartup() {
        return false;
    }

    @Override
    public void stop(Runnable callback) {

        stop();
        callback.run();
    }

    @Override
    public void start() {
        synchronized (lifecycleMonitor) {
            if (this.running) {
                return;
            }

            subscriptions.stream()
                    .filter(it -> !it.isActive())
                    .filter(it -> it instanceof TaskSubscription)
                    .map(TaskSubscription.class::cast)
                    .map(TaskSubscription::getTask)
                    .forEach(containerOptions.getExecutor()::execute);

            running = true;
        }
    }

    @Override
    public void stop() {
        synchronized (lifecycleMonitor) {
            if (this.running) {
                subscriptions.forEach(Cancelable::cancel);
                running = false;
            }
        }
    }

    @Override
    public boolean isRunning() {
        synchronized (this.lifecycleMonitor) {
            return running;
        }
    }

    @Override
    public int getPhase() {
        return Integer.MAX_VALUE;
    }


    @Override
    public Subscription register(QueueReadOptions<K> readOptions, MessageListener<V> listener) {
        return doRegister(getReadTask(readOptions, listener));
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    private StreamPollTask<K, V> getReadTask(QueueReadOptions<K> readOptions, MessageListener<V> listener) {

        Function<ReadOffset, Optional<CallBackData<V>>> readFunction = getReadFunction(readOptions);

        return new StreamPollTask<>(readOptions, listener, containerOptions.getErrorHandler(), readFunction, containerOptions.getPollTimeout());
    }

    private Function<ReadOffset, Optional<CallBackData<V>>> getReadFunction(QueueReadOptions<K> readOptions) {
        if (!readOptions.isAckQueueType()) {
            return (offset) -> readData(readOptions);
        }
        return (offset) -> readAckData(readOptions);
    }

    private Optional<CallBackData<V>> readAckData(QueueReadOptions<K> readOptions) {
        List<V> execute = template.execute(RedisScript.of(QUEUE_READ_ACK_AND_UPDATE_SCORE, List.class),
                Lists.newArrayList(readOptions.getAckKey()),
                String.valueOf(readOptions.getAckWaitBeforeRead().getSeconds()), String.valueOf(containerOptions.getBatchSize()));
        return Optional.ofNullable(execute).filter(it1 -> !CollectionUtils.isEmpty(it1)).map(it -> new CallBackData<>(it, putItBackFun(readOptions), ackFun(readOptions)));
    }

    private Optional<CallBackData<V>> readData(QueueReadOptions<K> readOptions) {
        List<V> execute = template.execute(RedisScript.of(QUEUE_READ_AND_ADD_ACK, List.class),
                Lists.newArrayList(readOptions.getKey(), readOptions.getAckKey()),
                String.valueOf(containerOptions.getBatchSize()));
        return Optional.ofNullable(execute).filter(it1 -> !CollectionUtils.isEmpty(it1)).map(it -> new CallBackData<>(it, putItBackFun(readOptions), ackFun(readOptions)));
    }

    private Consumer<List<V>> ackFun(QueueReadOptions<K> readOptions) {
        return data -> template.opsForZSet().remove(readOptions.getAckKey(), data.toArray());
    }

    private Consumer<List<V>> putItBackFun(QueueReadOptions<K> readOptions) {
        return data -> {
//        todo ???????????????????????????????????????????????????,???????????????????????????(left,right)
            template.execute(RedisScript.of(QUEUE_PUT_BACK_AND_REMOVE_ACK, Integer.class),
                    Lists.newArrayList(readOptions.getKey(), readOptions.getAckKey()),
                    data.stream().map(Object::toString).toArray());
        };
    }


    private Subscription doRegister(Task task) {

        Subscription subscription = new TaskSubscription(task);

        synchronized (lifecycleMonitor) {

            this.subscriptions.add(subscription);

            if (this.running) {
                containerOptions.getExecutor().execute(task);
            }
        }

        return subscription;
    }

    @Override
    public void remove(Subscription subscription) {

        synchronized (lifecycleMonitor) {

            if (subscriptions.contains(subscription)) {

                if (subscription.isActive()) {
                    subscription.cancel();
                }

                subscriptions.remove(subscription);
            }
        }
    }

    static class TaskSubscription implements Subscription {

        private final Task task;

        protected TaskSubscription(Task task) {
            this.task = task;
        }

        Task getTask() {
            return task;
        }

        @Override
        public boolean isActive() {
            return task.isActive();
        }

        @Override
        public boolean await(Duration timeout) throws InterruptedException {
            return task.awaitStart(timeout);
        }

        @Override
        public void cancel() throws DataAccessResourceFailureException {
            task.cancel();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;

            TaskSubscription that = (TaskSubscription) o;

            return ObjectUtils.nullSafeEquals(task, that.task);
        }

        @Override
        public int hashCode() {
            return ObjectUtils.nullSafeHashCode(task);
        }
    }

    enum LoggingErrorHandler implements ErrorHandler {

        INSTANCE;

        private final Log logger;

        LoggingErrorHandler() {
            this.logger = LogFactory.getLog(LoggingErrorHandler.class);
        }

        public void handleError(Throwable t) {

            if (this.logger.isErrorEnabled()) {
                this.logger.error("Unexpected error occurred in scheduled task.", t);
            }
        }
    }
}
