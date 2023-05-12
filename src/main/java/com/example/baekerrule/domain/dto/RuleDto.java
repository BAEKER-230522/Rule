package com.example.baekerrule.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RuleDto {
    private String name;
    private String about;
    private Integer xp;
    private Integer count;
    private String provider;
    private String difficulty;
}