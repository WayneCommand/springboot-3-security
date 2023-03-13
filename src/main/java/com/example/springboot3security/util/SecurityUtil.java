package com.example.springboot3security.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityUtil {

    public static String getToken(HttpServletRequest request) {
        final String header = request.getHeader("Authentication");
        if (StringUtils.hasText(header))
            return getToken(header);
        return null;
    }

    public static String getToken(String header) {
        if (header.startsWith("Bearer "))
            return header.split(" ")[1].trim();
        return null;
    }

    public static UserDetails userDetails(String token) {
        List<SimpleGrantedAuthority> authorities = null;
        String AuthoritiesArr = JwtUtil.getClaim(token, "authority");
        if (StringUtils.hasText(AuthoritiesArr)) {

            String[] authority = AuthoritiesArr.split(",");

            authorities = Stream.of(authority)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
        }

        // -1 为不正常的值，但不会出现，也不应该出现
        String id = JwtUtil.getClaimOrDefault(token, "id", "-1");
        return User.builder()
                .username(JwtUtil.getClaim(token, "username"))
                .password("")
                .authorities(authorities)
                .disabled(false)
                .build();

    }

}