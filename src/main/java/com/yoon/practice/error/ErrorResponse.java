package com.yoon.practice.error;

import lombok.*;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorResponse {

    // 오류 메세지
    private String message;

    // http 상태 코드
    private int status;

    // 에러가 발생한 필드명
    private List<CustomFieldError> fieldErrors;

    // 설정된 에러 코드
    private String code;

    static public ErrorResponse create() {
        return new ErrorResponse();
    }

    public ErrorResponse code(String code) {
        this.code = code;
        return this;
    }

    public ErrorResponse status(int status) {
        this.status = status;
        return this;
    }

    public ErrorResponse message(String message) {
        this.message = message;
        return this;
    }

    public ErrorResponse errors(Errors errors) {
        setFieldErrors(errors.getFieldErrors());
        return this;
    }

    public void setFieldErrors(List<FieldError> fieldErrors){
        this.fieldErrors = new ArrayList<>();

        fieldErrors.forEach(error -> {
            this.fieldErrors.add(new CustomFieldError(error.getCodes()[0], error.getRejectedValue()));
        });

    }

    @Getter
    @AllArgsConstructor
    public static class CustomFieldError {
        private String field;
        private Object value;
    }
}
