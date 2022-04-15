package com.example.springboot3security.service;

import com.example.springboot3security.model.User;
import com.example.springboot3security.util.JwtUtil;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class TokenService {

    private static final String ENCRYPT_KEY = "62da5956da04fdedd0ff08a3f8c812793ef4219cd0405d44bf5412f3264fecf0"; //sha256

    private static final String USER_ID_CLAIM = "userId";

    /**
     * 签发
     *
     * @return
     */
    public String sign(User user) {
        final Long userId = user.getUserId();

        final Map<String, String> payload = Map.of(
                USER_ID_CLAIM, String.valueOf(userId)
        );


        return JwtUtil.sign(payload, encrypt(String.valueOf(userId)));
    }



    /**
     * 验证
     */
    public boolean verify(String token) {

        // 判断是否被block了

        final String userId = JwtUtil.getClaim(token, USER_ID_CLAIM);
        final String secret = encrypt(userId);

        return JwtUtil.verify(token, secret);
    }

    // 一种稳定的加密
    private String encrypt(String userId) {
        return Sha512DigestUtils.shaHex(userId + ENCRYPT_KEY);
    }

}
