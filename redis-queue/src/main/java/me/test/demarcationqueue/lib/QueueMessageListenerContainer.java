package me.test.demarcationqueue.lib;

import org.springframework.context.SmartLifecycle;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.data.redis.stream.Subscription;

public interface QueueMessageListenerContainer<K, V> extends SmartLifecycle {

    static QueueMessageListenerContainer<String, String> create(
            RedisConnectionFactory connectionFactory) {
        return create(connectionFactory, QueueContainerOptions.<String, String>builder()
                .keySerializer(StringRedisSerializer.UTF_8)
                .valueSerializer(StringRedisSerializer.UTF_8)
                .build());
    }

    static <K, V> QueueMessageListenerContainer<K, V> create(
            RedisConnectionFactory connectionFactory, QueueContainerOptions<K, V> options) {
        return new DefaultQueueMessageListenerContainer<>(connectionFactory, options);
    }

//    default Subscription receive(K key, MessageListener<V> listener) {
//        return register(QueueReadOptions.<K>builder().key(key).build(), listener);
//    }

    default Subscription receive(QueueReadRegisterConfig<K,V> readRegisterConfig) {
        return register(readRegisterConfig.getReadOptions(), readRegisterConfig.getListener());
    }

    Subscription register(QueueReadOptions<K> readOptions, MessageListener<V> listener);

    void remove(Subscription subscription);

    interface QueueReadRegisterConfig<K,V>{
        QueueReadOptions<K> getReadOptions();
        MessageListener<V> getListener();
    }
}
