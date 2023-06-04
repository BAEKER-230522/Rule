package com.example.baekerrule.domain.dto.response;

import com.example.baekerrule.domain.dto.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateRuleResponse {
    @Schema(description = "생성 Id", example = "1")
    private Long id;
}
