package com.example.springboot3security.service;

import com.example.springboot3security.model.User;
import com.example.springboot3security.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public record UserService(UserRepository userRepository) {

    public User findUserById(Long userId) {
        return userRepository.findByUserId(userId);
    }

    public User findUserByName(String name) {
        return userRepository.findUserByUserName(name);
    }

}
