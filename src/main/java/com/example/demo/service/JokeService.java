package com.example.demo.service;

import com.example.demo.api.dto.response.JokeDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface JokeService {
    List<JokeDto> getJokes(Integer count);
}
