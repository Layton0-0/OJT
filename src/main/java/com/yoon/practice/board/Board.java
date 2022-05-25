package com.yoon.practice.board;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.yoon.practice.user.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Board {
    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardCode;

    @Column
    private String boardTitle;

    @Column
    private String boardContent;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    @PrePersist
    public void createdAt(){
        boardRegdate = LocalDateTime.now();
    }

    @Column
    private LocalDateTime boardRegdate;
}
