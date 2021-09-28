package me.test.demarcationqueue.lib;

import lombok.Builder;
import lombok.Getter;
import me.test.demarcationqueue.use.RedisLock;
import org.springframework.util.ErrorHandler;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.function.Predicate;

@Getter
@Builder
public class QueueReadOptions<K> {
    private K key;
    @Builder.Default
    private String queueType = "list"; //list/zset
    //        private final int count = 5;
    private K ackKey;
    private String lockKey;
    private RedisLock redisLock;
    private int ackScorePrefix;
    private ErrorHandler errorHandler;
    @Builder.Default
    private Predicate<Throwable> cancelSubscriptionOnError = t -> true;


    public Double getScore() {
        LocalDateTime now = LocalDateTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        LocalDateTime yesterday = LocalDateTime.of(now.minus(1, ChronoUnit.DAYS).toLocalDate(), LocalTime.MIN);

        return (double) (getAckScorePrefix() + Duration.between(yesterday, now).getSeconds());
    }

    public boolean needAck(){
        return Objects.nonNull(ackKey);
    }

    public boolean listQueueType() {
        return "list".equals(queueType);
    }

    public boolean hasLock(){
        return Objects.nonNull(redisLock) && Objects.nonNull(lockKey);
    }
}
