package com.yoon.practice.error.board;

import com.yoon.practice.error.CustomException;
import com.yoon.practice.error.ErrorCode;
import com.yoon.practice.error.ErrorResponse;
import org.springframework.validation.BindingResult;

public class DeleteByUnauthUserException extends CustomException {
    private static final long serialVersionUID = -2116671122895194101L;

    public DeleteByUnauthUserException(ErrorResponse.CustomFieldError customFieldError){
        super(ErrorCode.DELETE_BY_UNAUTH_USER, customFieldError);
    }
}
