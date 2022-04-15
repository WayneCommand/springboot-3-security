package com.example.springboot3security.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtil {

    public static String getCurrentToken() {
        throw new UnsupportedOperationException();
    }

    public static User getCurrentUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static Long getCurrentUserId() {
        throw new UnsupportedOperationException();
    }

}
