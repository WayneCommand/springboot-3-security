package com.example.springboot3security.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtil {

    public static String getCurrentToken() {
        throw new UnsupportedOperationException();
    }

    public static Long getCurrentUserId() {
        User user = (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return Long.valueOf(user.getUsername());
    }

}
