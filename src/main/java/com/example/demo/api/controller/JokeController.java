package com.example.demo.api.controller;

import com.example.demo.api.dto.request.JokeRequest;
import com.example.demo.api.dto.response.JokeDto;
import com.example.demo.api.dto.response.JokeResponseDto;
import com.example.demo.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class JokeController {
    private final JokeService jokeService;

    @GetMapping(value = "/jokes")
    public JokeResponseDto getJokes(JokeRequest jokeRequest) {
        if(jokeRequest.getCount()>100){
            throw new IllegalArgumentException("За один раз можно получить не более 100 шуток.");
        }
        List<JokeDto> list = jokeService.getJokes(jokeRequest.getCount());
        return new JokeResponseDto().setList(list);
    }
}
