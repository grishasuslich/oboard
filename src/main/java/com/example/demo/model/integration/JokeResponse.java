package com.example.demo.model.integration;

import lombok.Data;

import java.io.Serializable;

@Data
public class JokeResponse implements Serializable {
    private Long id;
    private String type;
    private String setup;
    private String punchline;
}
