package me.test.demarcationqueue.use;

import lombok.extern.slf4j.Slf4j;
import me.test.demarcationqueue.lib.MessageListener;
import me.test.demarcationqueue.lib.QueueMessageListenerContainer.QueueReadRegisterConfig;
import me.test.demarcationqueue.lib.QueueReadOptions;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Slf4j
@Component
public class AckQueueMessageListener extends BaseJobMessageListener {
    @Override
    protected void updateRunningNum(int num) {
        DemarcationFlag.getInstance().updateHighRunningNum(num);
    }

    @Component
    public static class AckQueueReadRegisterConfig implements QueueReadRegisterConfig<String, String> {
        private final QueueReadOptions<String> options;
        private final AckQueueMessageListener listener;

        public AckQueueReadRegisterConfig(AckQueueMessageListener listener) {
            Config config = Config.getInstance();
            this.options = buildReadOptions(config);
            this.listener = listener;
        }

        private QueueReadOptions<String> buildReadOptions(Config config) {
            return QueueReadOptions.<String>builder()
                    .ackKey(config.getAckKey())
                    .ackQueueType(true)
                    .ackWaitBeforeRead(Duration.ofSeconds(20))
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
