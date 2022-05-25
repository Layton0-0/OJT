package com.yoon.practice.board;

import com.yoon.practice.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board save(Board board) {
        return boardRepository.save(board);
    }
}
