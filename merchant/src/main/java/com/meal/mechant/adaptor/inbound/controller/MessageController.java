package com.meal.mechant.adaptor.inbound.controller;/*
package com.meal.mechant.adaptor.inbound.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.meal.mechant.RedisConfig;
import com.meal.mechant.domain.MyMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.stream.Record;
import org.springframework.data.redis.connection.stream.StreamRecords;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

*/
/**
 * @author Eason
 * @createTime 2019-02-18 下午 11:03
 * @description
 *//*

@RestController
@RequestMapping(produces = "application/json")
@RequiredArgsConstructor
public class MessageController {
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @GetMapping(value = "stream/pub")
    public void streamPub() {
        MyMessage message = MyMessage.builder().senderId(1L).receiverId(2L).content("message").build();
        Record<String, MyMessage> record = StreamRecords.objectBacked(message).withStreamKey(RedisConfig.streamName);
        redisTemplate.opsForStream().add(record);
//                redisTemplate.opsForList().leftPush("myList", objectMapper.writeValueAsString(message));
    }
}
*/
