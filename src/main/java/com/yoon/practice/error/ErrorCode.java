package com.yoon.practice.error;

import lombok.Getter;

import java.util.List;

@Getter
public enum ErrorCode {
    LOGIN_FAILED(404, "LOGIN_FAILED", "E001"),
    DELETE_BY_UNAUTH_USER(500, "DELETE_BY_UNAUTH_USER", "E002"),
    DUPLICATED_ID(505, "DUPLICATED_ID", "E003"),
    INVALID_PARAM(400, "INVALID_PARAM", "E004");

    // 오류 메세지
    private String message;

    // http 상태 코드
    private int status;

    // 설정된 에러 코드
    private String code;

    ErrorCode(int status, String message, String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
