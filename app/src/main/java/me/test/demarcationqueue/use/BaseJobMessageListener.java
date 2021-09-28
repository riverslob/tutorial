package me.test.demarcationqueue.use;

import lombok.extern.slf4j.Slf4j;
import me.test.demarcationqueue.lib.CallBackData;
import me.test.demarcationqueue.lib.MessageListener;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
public abstract class BaseJobMessageListener implements MessageListener<String> {


    @Override
    public void onMessage(final CallBackData<String> callBackData) {
        try {
            addRunningNum(callBackData.getData().size());
            execute(callBackData.getData(), wrapper(callBackData.getPutItBack()), wrapper(callBackData.getAck()));
            afterExecute(true);
        } catch (Exception e) {
            log.error("zrangebyscore({}, {})", "", e);
        }
    }

    private Consumer<List<String>> wrapper(Consumer<List<String>> callBackData) {
        return callBackData.andThen(this::subtractRunningNum);
    }

    protected void execute(List<String> data, Consumer<List<String>> putItBack, Consumer<List<String>> ack) {
        //定界
        log.info("size {} start demarcation,data {}", data.size(), String.join(",", data));
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(20));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ack.accept(data);
    }

    protected void afterExecute(boolean hasData) {
        if (!hasData) {
            sleep();
        }
    }

    protected void sleep() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void addRunningNum(int num) {
        updateRunningNum(num);
    }

    private void subtractRunningNum(List<String> it) {
        updateRunningNum(-1 * it.size());
    }

    protected abstract void updateRunningNum(int num);
}
