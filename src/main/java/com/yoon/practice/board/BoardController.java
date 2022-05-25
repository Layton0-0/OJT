package com.yoon.practice.board;

import com.yoon.practice.user.User;
import com.yoon.practice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
        // 유저 foreign key 연결
        board.setUser(userService.findByUserId(userId));
        boardService.save(board);
    }

    // Read
    @GetMapping("/read-all")
    public Page<Board> readAllBoard(Pageable pageable){
        return boardService.findAll(pageable);
    }

    // Update(다시)
    @PutMapping("/update")
    public void updateBoard(@ModelAttribute Board board, String userId){
        User user = userService.findByUserId(userId);

        // User가 가지고 있는 List<Board> 안에 해당 boardCode가 있는지 확인
        if(boardService.boardFromUser(userId).getBoardCode() == board.getBoardCode()){
            boardService.save(board);
        }
    }

    // Delete
    @DeleteMapping("delete")
    public void deleteBoard(){

    }
}
