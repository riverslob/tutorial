package me.test.demarcationqueue.use;

import lombok.Getter;

@Getter
public class Config {
    private static final Config instance = new Config();
    private static final String QUEUE_PREFIX_NAME = "delay-queue-";

    private final String highQueueKey = QUEUE_PREFIX_NAME + "high-keys";
    private final String normalQueueKey = QUEUE_PREFIX_NAME + "normal-keys";
    private final String ackKey = QUEUE_PREFIX_NAME + "acks";

    private final int queueFetchSize = 2;
    private final int sleepSecondsWhenNoData = 2;

    public static Config getInstance() {
        return instance;
    }


}
