package com.yoon.practice.error;

import com.yoon.practice.error.user.DuplicateIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;

@RestControllerAdvice
public class ErrorController {

//    @ExceptionHandler(Exception.class)
//    public Exception handleAllEx(Exception e) {
////        BindingResult bindingResult = ex.getBindingResult();
////        ErrorResponse errorResponse = ErrorResponse.create().errors(ex.getFieldErrors());
//        return e;
//    }

    // 4. 게시물 등록 시 parameter가 잘못 들어왔을 시
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ErrorResponse> handleUnexpectedTypeException(BindException e){
        BindingResult bindingResult = e.getBindingResult();
        ErrorCode errorCode = ErrorCode.INVALID_PARAM;
        ErrorResponse errorResponse = ErrorResponse.create().status(errorCode.getStatus()).code(errorCode.getCode()).errors(bindingResult).message(e.getMessage());

        return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }

    // 2. 게시물을 작성하지않은 사람이 게시물을 삭제할 때
    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e){
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse
                = ErrorResponse
                .create()
                .status(errorCode.getStatus())
                .code(errorCode.getCode()).error(e.getCustomFieldError())
                .message(e.toString());

        return new ResponseEntity<>(errorResponse, HttpStatus.resolve(errorCode.getStatus()));
    }
}
