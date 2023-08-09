package com.example.baekerrule.domain.dto;

import com.example.baekerrule.domain.Entity.Rule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleDto {
    @Schema(description = "RuleId", example = "1")
    private Long id;
    @Schema(description = "Rule 이름", example = "이름")
    private String name;
    @Schema(description = "Rule 소개", example = "소개")
    private String about;
    @Schema(description = "미션 성공시 획득 경험치", example = "10")
    private Integer xp;
    @Schema(description = "문제를 풀어야하는 숫자(스터디 단위)", example = "10")
    private Integer count;
    @Schema(description = "OJ사이트", example = "BaekJoon")
    private String provider;
    @Schema(description = "문제를 풀어야하는 난이도", example = "Gold")
    private String difficulty;

    public RuleDto(Rule rule) {
        this.id = rule.getId();
        this.name = rule.getName();
        this.about = rule.getAbout();
        this.xp = rule.getXp();
        this.count = rule.getCount();
        this.provider = rule.getProvider();
        this.difficulty = rule.getDifficulty();
    }
}


