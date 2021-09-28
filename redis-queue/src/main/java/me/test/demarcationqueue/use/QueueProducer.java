package me.test.demarcationqueue.use;

import lombok.RequiredArgsConstructor;
import me.test.demarcationqueue.use.HighQueueMessageListener.HighQueueReadRegisterConfig;
import me.test.demarcationqueue.use.NormalQueueMessageListener.NormalQueueReadRegisterConfig;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
@RequiredArgsConstructor
public class QueueProducer {
    private final StringRedisTemplate redisTemplate;
    private final HighQueueReadRegisterConfig highQueueReadRegisterConfig;
    private final NormalQueueReadRegisterConfig normalQueueReadRegisterConfig;

    public void produce(List<String> highPriorityContents, List<String> normalPriorityContents) {
        produce(highQueueReadRegisterConfig.getReadOptions().getKey(), highPriorityContents);
        produce(normalQueueReadRegisterConfig.getReadOptions().getKey(), normalPriorityContents);
    }

    private void produce(String queue, List<String> contents) {
        if (!CollectionUtils.isEmpty(contents)) {
            redisTemplate.opsForList().rightPushAll(queue, contents);
        }
    }
}
