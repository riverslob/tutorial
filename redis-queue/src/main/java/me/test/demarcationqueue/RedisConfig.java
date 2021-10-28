
package me.test.demarcationqueue;

import lombok.RequiredArgsConstructor;
import me.test.demarcationqueue.lib.MessageListener;
import me.test.demarcationqueue.lib.QueueMessageListenerContainer;
import me.test.demarcationqueue.lib.QueueMessageListenerContainer.QueueReadRegisterConfig;
import me.test.demarcationqueue.lib.QueueReadOptions;
import me.test.demarcationqueue.use.AckQueueMessageListener;
import me.test.demarcationqueue.use.Config;
import me.test.demarcationqueue.use.HighQueueMessageListener;
import me.test.demarcationqueue.use.RedisLockImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    @Bean
    public QueueMessageListenerContainer<String, String> subscription(RedisConnectionFactory factory, List<QueueReadRegisterConfig<String, String>> list) {
        QueueMessageListenerContainer<String, String> container = QueueMessageListenerContainer.create(factory);
        list.forEach(container::receive);
        container.start();
        return container;
    }

    @Profile("prod")
    @Bean("myStringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Profile("dev")
    @Bean("myStringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate2(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
