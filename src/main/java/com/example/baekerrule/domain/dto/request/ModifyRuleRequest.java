package com.example.baekerrule.domain.dto.request;

import lombok.Data;

@Data
public class ModifyRuleRequest {
    private String name;
    private String about;
    private Integer xp;

    private Integer count;
    private String provider;
    private String difficulty;
}
