package com.example.baekerrule.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRuleRequest {
    @Schema(description = "규칙 명", example = "이름")
    @NotEmpty
    private String name;

    @Schema(description = "규칙을 소개함", example = "소개")
    private String about;

    @Schema(description = "규칙을 성공했을때 오르는 경험치", example = "1")
    private Integer xp;

    @Schema(description = "문제를 얼마나 풀어야 하는지 정함(스터디 단위)", example = "1")
    private Integer count;
    @Schema(description = "OJ 사이트", example = "BaekJoon")
    private String provider;

    @Schema(description = "풀어야 하는 난이도를 정의", example = "Gold")
    @NotEmpty
    private String difficulty;
}
