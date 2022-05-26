package com.yoon.practice.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public Page<Board> findAll(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public void deleteById(Long boardCode){
        boardRepository.deleteById(boardCode);
    };

    public Board getReferenceById(Long boardCode){
        return boardRepository.getReferenceById(boardCode);
    };
}
