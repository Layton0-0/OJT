package com.yoon.practice.board;

import com.google.gson.JsonObject;
import com.yoon.practice.error.user.DuplicateIdException;
import com.yoon.practice.user.User;
import com.yoon.practice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private UserService userService;

    @GetMapping("/exception")
    public String exception01(){
        throw new DuplicateIdException();
    }

    // Create
    @PostMapping("/create")
    public ResponseEntity<DataResponse> createBoard(@ModelAttribute Board board, String userId){
        // 유저 foreign key 연결
        board.setUser(userService.findByUserId(userId));
        boardService.save(board);

        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        dataResponse.setStatus(StatusEnum.OK.statusCode);
        dataResponse.setMessage(StatusEnum.OK.code);
        dataResponse.setData(board);

        return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);


    }

    // Read
    @GetMapping("/read-all")
    public ResponseEntity<DataResponse> readAllBoard(@PageableDefault(page=0, size=10) Pageable pageable){
        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        dataResponse.setStatus(StatusEnum.OK.statusCode);
        dataResponse.setMessage(StatusEnum.OK.code);
        dataResponse.setData(boardService.findAll(pageable));

        return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
    }

    @GetMapping("/read-one")
    public ResponseEntity<DataResponse>  readOneBoard(Long boardCode){
        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        dataResponse.setStatus(StatusEnum.OK.statusCode);
        dataResponse.setMessage(StatusEnum.OK.code);
        dataResponse.setData(boardService.getReferenceById(boardCode));

        return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
    }

    // pageable 객체 사용 안한 페이징
    @GetMapping("/read-page")
    public ResponseEntity<DataResponse> readPageByPage(int page){
        PagingObject pagingObject = new PagingObject();
        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();

        List<Board> boardList = boardService.findAll();
        pagingObject.setPage(page);
        pagingObject.setTotalPage(boardList.size()/10 + 1);
        pagingObject.setTotalRecordCount(boardList.size());
        pagingObject.setData(boardList.subList(pagingObject.getFirstRecord(), pagingObject.getLastRecord()));

        dataResponse.setStatus(StatusEnum.OK.statusCode);
        dataResponse.setMessage(StatusEnum.OK.code);
        dataResponse.setData(pagingObject);
        return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
    }

    // Update(다시)
    @PutMapping("/update")
    public ResponseEntity<DataResponse> updateBoard(@ModelAttribute Board newBoard, String userId){
        Board board = boardService.getReferenceById(newBoard.getBoardCode());
        User user = board.getUser();

        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        board.setBoardTitle(newBoard.getBoardTitle());
        board.setBoardContent(newBoard.getBoardContent());
        board.setBoardRegdate(LocalDateTime.now());

        // UserId가 게시판의 userId와 같은지 확인 후 저장
        if(userId.equals(user.getUserId())){
            dataResponse.setStatus(StatusEnum.OK.statusCode);
            dataResponse.setMessage(StatusEnum.OK.code);
            dataResponse.setData(boardService.save(board));
            return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
        } else {
            dataResponse.setStatus(StatusEnum.NOT_FOUND.statusCode);
            dataResponse.setMessage(StatusEnum.NOT_FOUND.code);
            dataResponse.setData(board);
            return new ResponseEntity<>(dataResponse, headers, HttpStatus.NOT_FOUND);
        }

    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<DataResponse> deleteBoard(Long boardCode, String userId, @PageableDefault(page=0, size=10) Pageable pageable){
        Board board = boardService.getReferenceById(boardCode);
        User user = board.getUser();

        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if(userId.equals(user.getUserId())){
            boardService.deleteById(board.getBoardCode());
            dataResponse.setStatus(StatusEnum.OK.statusCode);
            dataResponse.setMessage(StatusEnum.OK.code);
            dataResponse.setData(boardService.findAll(pageable));
            return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
        } else {
            dataResponse.setStatus(StatusEnum.NOT_FOUND.statusCode);
            dataResponse.setMessage(StatusEnum.NOT_FOUND.code);
            dataResponse.setData(boardService.findAll(pageable));
            return new ResponseEntity<>(dataResponse, headers, HttpStatus.NOT_FOUND);
        }
    }

}
