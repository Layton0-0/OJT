package com.yoon.practice.error.board;

import com.yoon.practice.error.CustomException;
import com.yoon.practice.error.ErrorCode;

public class InvalidParamException extends CustomException {

    private static final long serialVersionUID = -2116671122895194101L;

    public InvalidParamException(){
        super(ErrorCode.INVALID_PARAM);
    }
}
