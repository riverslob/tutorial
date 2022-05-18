package com.example.consumer.controller;

import com.example.consumer.dto.HelloDto;
import com.example.consumer.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    /*@GetMapping("/")
    public String hello(Model model) {
        model.addAttribute("helloDto", helloService.execute2("PREFIX ===== "));
        return "hello";
    } */

    @GetMapping("/hello")
    public HelloDto hello() {
        HelloDto helloDto = helloService.execute("PREFIX ===== ");
//        model.addAttribute("helloDto", helloDto);
//        return "hello";
        return helloDto;
    }
}
