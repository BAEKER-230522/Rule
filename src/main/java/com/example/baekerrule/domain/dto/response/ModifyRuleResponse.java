package com.example.baekerrule.domain.dto.response;

import com.example.baekerrule.domain.Entity.Rule;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ModifyRuleResponse {
    @Schema(description = "수정된 Id", example = "이름수정")
    private String name;
    @Schema(description = "수정된 소개", example = "소개수정")
    private String about;
    @Schema(description = "수정된 경험치", example = "10")
    private Integer xp;
    @Schema(description = "수정된 문제 풀이 수", example = "10")
    private Integer count;
    @Schema(description = "수정된 OJ 사이트", example = "programmers")
    private String provider;
    @Schema(description = "수정 된 난이도", example = "Silver")
    private String difficulty;

    public ModifyRuleResponse(Rule rule) {
        this.name = rule.getName();
        this.about = rule.getAbout();
        this.xp = rule.getXp();
        this.count = rule.getCount();
        this.provider = rule.getProvider();
        this.difficulty = rule.getDifficulty();
    }
}
