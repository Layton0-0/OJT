package com.yoon.practice.board;

import com.google.gson.JsonObject;
import com.yoon.practice.user.User;
import com.yoon.practice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    // Create
    @PostMapping("/create")
    public void createBoard(@ModelAttribute Board board, String userId){
        DataResponse dataResponse = new DataResponse();

        // 유저 foreign key 연결
        board.setUser(userService.findByUserId(userId));
        boardService.save(board);
    }

    // Read
    @GetMapping("/read-all")
    public Page<Board> readAllBoard(@PageableDefault(page=0, size=10) Pageable pageable){
        return boardService.findAll(pageable);
    }

    @GetMapping("/read-one")
    public Board readOneBoard(Long boardCode){
        return boardService.getReferenceById(boardCode);
    }

    // Update(다시)
    @PutMapping("/update")
    public Board updateBoard(@ModelAttribute Board newBoard, String userId){
        Board board = boardService.getReferenceById(newBoard.getBoardCode());
        User user = board.getUser();

        board.setBoardTitle(newBoard.getBoardTitle());
        board.setBoardContent(newBoard.getBoardContent());
        board.setBoardRegdate(LocalDateTime.now());

        // UserId가 게시판의 userId와 같은지 확인 후 저장
        if(userId.equals(user.getUserId())){
            return boardService.save(board);
        } else {
            return board;
        }
    }

    // Delete
    @DeleteMapping("/delete")
    public void deleteBoard(Long boardCode, String userId){
        Board board = boardService.getReferenceById(boardCode);
        User user = board.getUser();
        if(userId.equals(user.getUserId())){
            boardService.deleteById(board.getBoardCode());
        }

    }
}
