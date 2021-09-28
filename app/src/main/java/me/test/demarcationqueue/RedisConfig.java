
package me.test.demarcationqueue;

import lombok.RequiredArgsConstructor;
import me.test.demarcationqueue.lib.QueueMessageListenerContainer;
import me.test.demarcationqueue.lib.QueueMessageListenerContainer.QueueReadRegisterConfig;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import java.io.IOException;
import java.util.List;

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

    @Bean(destroyMethod="shutdown")
    RedissonClient redisson() throws IOException {
        //1、创建配置
        Config config = new Config();
        config.useSingleServer()
                .setAddress("192.168.43.129:6379");
        return Redisson.create(config);
    }
}
