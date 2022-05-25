package com.yoon.practice.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.yoon.practice.board.Board;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class User {
    @Column
    @Id
    private String userId;

    @Column
    private String userPw;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Board> board;
}
