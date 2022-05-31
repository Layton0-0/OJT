package com.yoon.practice.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yoon.practice.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board {
    // PK
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCode;

    // 게시판 제목
    @Column
    @NotNull(message = "제목을 입력해주세요")
    private String boardTitle;

    // 게시판 내용
    @Column
    @NotNull(message = "내용을 입력해주세요")
    private String boardContent;

    // 게시판 작성자
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    // 등록일 초기화
    @PrePersist
    public void createdAt(){
        this.boardRegdate = LocalDateTime.now();
    }

    // 게시판 등록일
    @Column
    private LocalDateTime boardRegdate;
}
