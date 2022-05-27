package com.yoon.practice.error;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MyAdvice {
    @ExceptionHandler(Exception.class)
    public String handleEx() {
        return "handle handle";
    }
}
