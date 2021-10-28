package tutorial.app;

import me.test.demarcationqueue.Application;
import me.test.demarcationqueue.use.Config;
import me.test.demarcationqueue.use.QueueProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static me.test.demarcationqueue.lib.LuaScriptConst.QUEUE_READ_AND_ADD_ACK;

@SpringBootTest(classes = Application.class)
class QueueProducerTest {
    @Autowired
    private QueueProducer queueProducer;
    @Autowired
    private StringRedisTemplate redisTemplate;



    static String ddd2 =
            "local list = redis.call('lrange',KEYS[1],0,ARGV[1]) \n  if !rawequal(next(list), nil) then  \n  return 0; end \n  return list;";

    //    @Disabled
    @Test
    public void should_process_data_when_queue_data_add() {
        //given
//        RDQueue rdQueue = new RDQueue(redisTemplate, redisLock, new DemarcationCallBack());

        //when
        List<String> highPriorityContents = getHighPriorityContents(10);
        List<String> normalPriorityContents = new ArrayList<>();//getNormalPriorityContents(100);
        queueProducer.produce(highPriorityContents, normalPriorityContents);

        //then
        List<String> range = redisTemplate.opsForList().range(Config.getInstance().getHighQueueKey(), 0, -1);
        Assertions.assertIterableEquals(highPriorityContents, range);
    }

    @Test
    public void testScript() {
        Config config = Config.getInstance();
        ArrayList<String> keys = new ArrayList<>();
        keys.add(config.getHighQueueKey());
        keys.add(config.getAckKey());
        List<String> execute = redisTemplate.execute(RedisScript.of(QUEUE_READ_AND_ADD_ACK, List.class), keys, config.getQueueFetchSize()+"","1");
        System.out.println("result ----");
        System.out.println(execute);
    }

    private List<String> getHighPriorityContents(int count) {
        return Stream.iterate(0, it -> it + 1).limit(count).map(it -> "high-task-" + it).collect(Collectors.toList());
    }

    private List<String> getNormalPriorityContents(int count) {
        return Stream.iterate(0, it -> it + 1).limit(count).map(it -> "normal-task-" + it).collect(Collectors.toList());
    }
}