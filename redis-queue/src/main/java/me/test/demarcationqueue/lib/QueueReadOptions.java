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
    private boolean ackQueueType = false;
    //        private final int count = 5;
    private K ackKey;
    /**
     * ack得分附加值。1-9，越小优先级越高
     */
//    private int ackScorePrefix;
    /**
     * 需ack处理的时间。放入时间和当前时间差如果大于该时间就处理
     */
    @Builder.Default
    private Duration ackWaitBeforeRead = Duration.ofMinutes(30);
    private ErrorHandler errorHandler;
    @Builder.Default
    private Predicate<Throwable> cancelSubscriptionOnError = t -> true;


    public boolean needAck(){
        return Objects.nonNull(ackKey);
    }

}
