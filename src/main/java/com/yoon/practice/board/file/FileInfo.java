package com.yoon.practice.board.file;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yoon.practice.board.Board;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class FileInfo {
    // 고유 번호(PK)
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileCode;

    // 게시판 고유 번호(FK)
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "board_code")
    private Board board;

    // 원본 파일명
    private String fileName;

    // 저장 경로
    private String filePath;

    // 파일 확장자 명
    private String fileExtention;

}
