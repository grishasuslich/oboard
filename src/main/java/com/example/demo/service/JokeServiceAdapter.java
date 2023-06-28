package com.example.demo.service;

import com.example.demo.model.integration.JokeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class JokeServiceAdapter {
    private final RestTemplate restTemplate;
    public JokeResponse getJoke() {
        String url = "https://official-joke-api.appspot.com/random_joke";
        return restTemplate.getForObject(url, JokeResponse.class);
    }
}
