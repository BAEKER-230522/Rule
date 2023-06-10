package com.example.baekerrule.domain.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Patch 할때 사용")
public record UpdateRequest(String name, String about, String xp, String count, String provider, String difficulty) {
}
