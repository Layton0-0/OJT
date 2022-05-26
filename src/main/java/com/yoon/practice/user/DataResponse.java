package com.yoon.practice.user;

import lombok.Data;

@Data
public class DataResponse {
    // http 상태 코드
    private int status;
    
    // 메세지
    private String message;
    
    // 응답 데이터
    private Object data;
}
