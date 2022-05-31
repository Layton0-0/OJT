package com.yoon.practice.error.user;

import com.yoon.practice.error.CustomException;
import com.yoon.practice.error.ErrorCode;
import com.yoon.practice.error.ErrorResponse;
import org.springframework.validation.BindingResult;

public class LoginFailException extends CustomException {
    private static final long serialVersionUID = -2116671122895194101L;

    public LoginFailException(ErrorResponse.CustomFieldError customFieldError) {
        super(ErrorCode.LOGIN_FAILED, customFieldError);
    }
}
