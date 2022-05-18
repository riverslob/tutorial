package com.example.consumer.service;

import com.example.consumer.dto.HelloDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient(name="PRODUCER",url = "${producer.baseurl}",configuration = FeignConfigure.class)
public interface HelloClient {
    @GetMapping("/api/hello")
    HelloDto hello();
}
