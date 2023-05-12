package com.example.baekerrule.domain.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateRuleRequest {
    @NotEmpty
    private String name;

    private String about;

    private Integer xp;

    private Integer count;
    private String provider;

    private String difficulty;
}
