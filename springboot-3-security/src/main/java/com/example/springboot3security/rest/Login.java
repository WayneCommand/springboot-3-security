package com.example.springboot3security.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class Login {

    @PostMapping("/login")
    public ResponseEntity<Token> login() {

        return null;
    }

    record Token(String jwt) {

    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String password = passwordEncoder.encode("password");

        System.out.println(password);


    }

}
