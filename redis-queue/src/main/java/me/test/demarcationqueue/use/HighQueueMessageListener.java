package me.test.demarcationqueue.use;

import lombok.extern.slf4j.Slf4j;
import me.test.demarcationqueue.lib.MessageListener;
import me.test.demarcationqueue.lib.QueueMessageListenerContainer.QueueReadRegisterConfig;
import me.test.demarcationqueue.lib.QueueReadOptions;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HighQueueMessageListener extends BaseJobMessageListener {
    @Override
    protected void updateRunningNum(int num) {
        DemarcationFlag.getInstance().updateHighRunningNum(num);
    }

    @Component
    public static class HighQueueReadRegisterConfig implements QueueReadRegisterConfig<String, String> {
        private final QueueReadOptions<String> options;
        private final HighQueueMessageListener listener;

        public HighQueueReadRegisterConfig(HighQueueMessageListener listener, RedisLock redisLock) {
            Config config = Config.getInstance();
            this.options = buildReadOptions(redisLock, config);
            this.listener = listener;
        }

        private QueueReadOptions<String> buildReadOptions(RedisLock redisLock, Config config) {
            return QueueReadOptions.<String>builder()
                    .key(config.getHighQueueKey())
                    .ackKey(config.getAckKey())
                    .lockKey(config.getLockKey())
                    .redisLock(redisLock)
                    .ackScorePrefix(config.getHighQueueAckScorePrefix())
                    .build();
        }

        @Override
        public QueueReadOptions<String> getReadOptions() {
            return options;
        }

        @Override
        public MessageListener<String> getListener() {
            return listener;
        }
    }
}
