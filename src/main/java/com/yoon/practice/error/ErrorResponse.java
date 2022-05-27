package com.yoon.practice.error;

import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public class ErrorResponse {

    // 오류 메세지
    private String message;

    // http 상태 코드
    private int status;

    // 에러가 발생한 필드명
    private List<FieldError> fieldErrors;

    // 설정된 에러 코드
    private String code;

    public static class FieldError {

    }
}
