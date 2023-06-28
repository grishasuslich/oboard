package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    @Bean(name = "jokesThreadPoolTaskExecutor")
    public Executor jokesThreadPoolTaskExecutor() {
        return Executors.newFixedThreadPool(10);
    }
}
