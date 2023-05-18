package com.example.baekerrule.domain.dto;

import com.example.baekerrule.domain.Entity.Rule;
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

    public RuleDto(Rule rule) {
        this.name = rule.getName();
        this.about = rule.getAbout();
        this.xp = rule.getXp();
        this.count = rule.getCount();
        this.provider = rule.getProvider();
        this.difficulty = rule.getDifficulty();
    }
}