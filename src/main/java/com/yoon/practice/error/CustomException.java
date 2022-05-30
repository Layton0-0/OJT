package com.yoon.practice.error;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;

@Getter
@NoArgsConstructor
public class CustomException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }


}
