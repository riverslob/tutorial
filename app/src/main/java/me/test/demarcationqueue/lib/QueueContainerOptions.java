package me.test.demarcationqueue.lib;

import lombok.Builder;
import lombok.Getter;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.util.concurrent.Executor;

@Getter
@Builder
public class QueueContainerOptions<K, V> {
    @Builder.Default
    private Duration pollTimeout = Duration.ofSeconds(2);
    @Builder.Default
    private Integer batchSize = 3;
    private RedisSerializer<K> keySerializer;
    private RedisSerializer<V> valueSerializer;
    @Builder.Default
    private ErrorHandler errorHandler = DefaultQueueMessageListenerContainer.LoggingErrorHandler.INSTANCE;
    @Builder.Default
    private Executor executor = new SimpleAsyncTaskExecutor();
}
