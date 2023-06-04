package com.example.baekerrule.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ModifyRuleRequest {
    @Schema(description = "수정하고싶은 규칙 명", example = "이름수정")
    private String name;

    @Schema(description = "수정하고싶은 규칙소개", example = "소개수정")
    private String about;

    @Schema(description = "수정하고싶은 경험치", example = "10")
    private Integer xp;

    @Schema(description = "수정 하고싶은 문제풀이 수", example = "10")
    private Integer count;
    @Schema(description = "수정하고 싶은 OJ 사이트", example = "programmers")
    private String provider;

    @Schema(description = "수정하고싶은 난이도", example = "Silver")
    private String difficulty;
}
