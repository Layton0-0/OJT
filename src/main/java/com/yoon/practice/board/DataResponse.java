package com.yoon.practice.board;

import lombok.Data;

import javax.persistence.Entity;

@Data
public class DataResponse {
    // http 상태 코드
    private int status;
    
    // 메세지
    private String message;
    
    // 응답 데이터
    private Object data;
}
