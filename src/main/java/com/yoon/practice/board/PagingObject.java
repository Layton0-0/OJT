package com.yoon.practice.board;

import lombok.*;

import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@NoArgsConstructor
@Getter
@Setter
public class PagingObject {
    // 현재 페이지
    private int page;
    
    // 페이지 당 표시 컨텐츠 수
    private int perPage = 10;
    
    // 전체 페이지 수
    private int totalPage;
    
    // 전체 컨텐츠 수
    private int totalRecordCount;

    @PreUpdate
    public void updateAt(){
        this.firstRecord = page * perPage;
        if(totalRecordCount % perPage == 0){
            this.lastRecord = firstRecord + perPage - 1;
        } else {
            this.lastRecord = totalRecordCount % perPage;
        }
    }

    // 목록 첫번째 컨텐츠(2페이지면 레코드 중 index 10번 레코드부터)
    private int firstRecord;

    // 목록 마지막 컨텐츠
    private int lastRecord;
    
    // 페이징 객체, 게시판 리스트
    private Object data;

    @PrePersist
    public void createdAt() {
        this.page = 0;
        this.firstRecord = 0;
        this.lastRecord = 0;
    }

}
