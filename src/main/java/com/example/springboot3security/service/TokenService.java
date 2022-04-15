package com.example.springboot3security.service;

import com.example.springboot3security.util.JwtUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TokenService {

    private static final String ENCRYPT_KEY = "ccf0a763e1c0dc55d29ef500fd4d43abebb24690ca2764476a68ea15c6b5d553"; //sha256

    /**
     * 签发
     *
     * @return
     */
    public String sign(User user) {
        String username = user.getUsername();
        Collection<GrantedAuthority> authorities = user.getAuthorities();


        final Map<String, String> payload = Map.of(
                "user_name", username,
                "authorities", authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(","))
        );


        return JwtUtil.sign(payload, encrypt(username));
    }



    /**
     * 验证
     */
    public boolean verify(String token) {

        // 判断是否被block了

        final String userId = JwtUtil.getClaim(token, "user_name");
        final String secret = encrypt(userId);

        return JwtUtil.verify(token, secret);
    }

    // 一种稳定的加密
    private String encrypt(String userId) {
        return Sha512DigestUtils.shaHex(userId + ENCRYPT_KEY);
    }

}
