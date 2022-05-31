package com.yoon.practice.user;

import com.yoon.practice.board.DataResponse;
import com.yoon.practice.error.ErrorResponse;
import com.yoon.practice.error.user.DuplicateIdException;
import com.yoon.practice.error.user.LoginFailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // transactional은 왜 쓰는가?
    // 1. 회원가입
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<DataResponse> register(@ModelAttribute User user){
        // 유저 도메인에 비밀번호를 해시화
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));

        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if(userService.findByUserId(user.getUserId()) != null){
            ErrorResponse.CustomFieldError customFieldError= new ErrorResponse.CustomFieldError("User", user.getUserId(), "아이디 중복");
            throw new DuplicateIdException(customFieldError);
        }

        // UserId가 게시판의 userId와 같은지 확인 후 저장
        if(userService.save(user) != null){
            dataResponse.setStatus(com.yoon.practice.user.StatusEnum.OK.statusCode);
            dataResponse.setMessage(com.yoon.practice.user.StatusEnum.OK.code);
            dataResponse.setData(user);
            return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
        } else {
            dataResponse.setStatus(com.yoon.practice.user.StatusEnum.NOT_FOUND.statusCode);
            dataResponse.setMessage(StatusEnum.NOT_FOUND.code);
            return new ResponseEntity<>(dataResponse, headers, HttpStatus.NOT_FOUND);
        }
    }

    // 2. 로그인
    @GetMapping(value = "/login")
    public ResponseEntity<DataResponse> login(String userId, String userPw) {
        User user = userService.findByUserId(userId);

        DataResponse dataResponse = new DataResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        if(userService.findByUserId(userId) == null){
            ErrorResponse.CustomFieldError customFieldError= new ErrorResponse.CustomFieldError("User", userId, "아이디가 존재하지 않습니다");
            throw new LoginFailException(customFieldError);
        }

        // Id에 Pw가 맞는지 확인 후 로그인 통과
        if(passwordEncoder.matches(userPw, user.getUserPw())){
            dataResponse.setStatus(com.yoon.practice.user.StatusEnum.OK.statusCode);
            dataResponse.setMessage(com.yoon.practice.user.StatusEnum.OK.code);
            // PK 반환
            dataResponse.setData(user.getUserId());
            return new ResponseEntity<>(dataResponse, headers, HttpStatus.OK);
        } else {
            ErrorResponse.CustomFieldError customFieldError= new ErrorResponse.CustomFieldError("User", userPw, "비밀번호가 틀립니다");
            throw new LoginFailException(customFieldError);
//            dataResponse.setStatus(com.yoon.practice.user.StatusEnum.NOT_FOUND.statusCode);
//            dataResponse.setMessage(StatusEnum.NOT_FOUND.code);
//            return new ResponseEntity<>(dataResponse, headers, HttpStatus.NOT_FOUND);
        }
    }

    // 3. 게시판

    @GetMapping("/test")
    public String test(String word){
        return word;
    }
}
