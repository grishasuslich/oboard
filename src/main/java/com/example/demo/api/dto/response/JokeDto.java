package com.example.demo.api.dto.response;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class JokeDto {
    private Long id;
    private String type;
    private String setup;
    private String punchline;
}
