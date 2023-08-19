package com.example.baekerrule.error;

import lombok.Getter;

@Getter
public enum ErrorResponse {
    NOT_FOUND_RULE("해당 규칙을 찾을 수 없습니다.", 404),
    NOT_ADMIN_AUTHORITY("관리자 권한이 없습니다.", 403),
    NUMBER_FORMAT_EXCEPTION("숫자 형식이 아닙니다.", 400),;

    private final String message;
    private final int status;

    ErrorResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
