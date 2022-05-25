package com.yoon.practice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User findByUserId(String userId){
        return userRepository.findByUserId(userId);
    };

    public Page<User> findAll(Pageable pageable){
        return (Page<User>) userRepository.findAll();
    }

    public User save(User user) {
        return userRepository.save(user);
    }

}
