package com.example.consumer;


import com.example.consumer.filter.LoggingFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
//@EnableDiscoveryClient
//@EnableCircuitBreaker
@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    /*@Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
*/
    @Bean
    public FilterRegistrationBean loggingFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean(new LoggingFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(Integer.MIN_VALUE);
        return bean;
    }
}
