package me.test.demarcationqueue.use;

import lombok.extern.slf4j.Slf4j;
import me.test.demarcationqueue.lib.MessageListener;
import me.test.demarcationqueue.lib.QueueMessageListenerContainer;
import me.test.demarcationqueue.lib.QueueReadOptions;
import org.springframework.stereotype.Component;

/**
 * AckMessageJob
 *
 * @author biezhi
 * @date 2019/11/21
 */
@Slf4j
@Component
public class NormalQueueMessageListener extends BaseJobMessageListener {

    @Override
    public void preRead() {
//        int count = 0;
        while (DemarcationFlag.getInstance().getHighRunningNum() > 0) {
            //            if (!(highRunningNum > 0 && count < 10)) break;
            log.error("now have {} high task run, need wait", DemarcationFlag.getInstance().getHighRunningNum());
            //todo 使用信号量代替sleep
            sleep();
//            count++;
        }
    }

    @Override
    protected void updateRunningNum(int num) {
        DemarcationFlag.getInstance().updateNormalRunningNum(num);
    }

    @Component
    public static class NormalQueueReadRegisterConfig implements QueueMessageListenerContainer.QueueReadRegisterConfig<String, String> {
        private final QueueReadOptions<String> options;
        private final NormalQueueMessageListener listener;

        public NormalQueueReadRegisterConfig(NormalQueueMessageListener listener, RedisLock redisLock) {
            Config config = Config.getInstance();
            this.options = buildReadOptions(redisLock, config);
            this.listener = listener;
        }

        private QueueReadOptions<String> buildReadOptions(RedisLock redisLock, Config config) {
            return QueueReadOptions.<String>builder()
                    .key(config.getNormalQueueKey())
                    .ackKey(config.getAckKey())
                    .lockKey(config.getLockKey())
                    .redisLock(redisLock)
                    .ackScorePrefix(config.getNormalQueueAckScorePrefix())
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
