package com.example.demo.api.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class JokeResponseDto {
    private List<JokeDto> list;
}
