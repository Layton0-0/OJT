package com.yoon.practice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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
    public Boolean register(@ModelAttribute User user){
        // 유저 도메인에 비밀번호를 해시화
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));
        if(userService.save(user) != null){
            return true;
        } else {
            return false;
        }
    }

    // 2. 로그인
    @GetMapping(value = "/login")
    public String login(String userId, String userPw) {
        User user = userService.findByUserId(userId);
        if(passwordEncoder.matches(userPw, user.getUserPw())){
            // PK 반환
            return user.getUserId();
        } else {
            return null;
        }
    }

    // 3. 게시판

    @GetMapping("/test")
    public String test(String word){
        return word;
    }
}
