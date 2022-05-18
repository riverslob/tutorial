package com.example.consumer.service;

import com.example.consumer.dto.HelloDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

//    private final RestTemplate restTemplate;

    private final HelloClient helloClient;

    public HelloService(HelloClient helloClient) {
        this.helloClient = helloClient;
    }

    public HelloDto execute(String prefix) {
//        HelloDto helloDto = restTemplate.getForObject(
//                "http://producer/api/hello", HelloDto.class);
        HelloDto helloDto = helloClient.hello();
        helloDto.setMessage(prefix + " : " + helloDto.getMessage());
        return helloDto;
    }

    public HelloDto execute2Fallback(String prefix, Throwable throwable) {
        logger.error("execute2Fallback Prefix = " + prefix);
        logger.error(throwable.getMessage());
        HelloDto helloDto = new HelloDto();
        helloDto.setMessage(prefix + " : This is a default message");
        return helloDto;
    }
}
