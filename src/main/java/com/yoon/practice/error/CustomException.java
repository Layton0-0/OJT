package com.yoon.practice.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.io.Serial;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;
    private ErrorResponse.CustomFieldError customFieldError;

    public CustomException(ErrorCode errorCode, ErrorResponse.CustomFieldError customFieldError) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.customFieldError = customFieldError;
    }


}
