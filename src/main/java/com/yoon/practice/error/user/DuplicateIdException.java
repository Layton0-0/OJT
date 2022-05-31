package com.yoon.practice.error.user;

import com.yoon.practice.error.CustomException;
import com.yoon.practice.error.ErrorCode;
import com.yoon.practice.error.ErrorResponse;
import org.springframework.validation.BindingResult;

import java.io.Serial;

public class DuplicateIdException extends CustomException {
    @Serial
    private static final long serialVersionUID = -2116671122895194101L;

    public DuplicateIdException(ErrorResponse.CustomFieldError customFieldError){
        super(ErrorCode.DUPLICATED_ID, customFieldError);
    }
}
