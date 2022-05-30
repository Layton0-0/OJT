package com.yoon.practice.error;

import com.yoon.practice.error.user.DuplicateIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorController {
//    @ExceptionHandler(Exception.class)
//    public Exception handleEx(Exception ex) {
//        return ex;
//    }

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        // ResponseEntity<ErrorResponse>
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response
                = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(e.toString());

        return new ResponseEntity<>(response, HttpStatus.resolve(errorCode.getStatus()));
    }
}
