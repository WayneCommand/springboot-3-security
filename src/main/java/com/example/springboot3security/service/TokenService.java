package com.example.springboot3security.service;

import com.example.springboot3security.SecurityProperties;
import com.example.springboot3security.security.SecurityConst;
import com.example.springboot3security.security.store.InMemoryTokenStore;
import com.example.springboot3security.security.store.TokenDetail;
import com.example.springboot3security.security.store.TokenStore;
import com.example.springboot3security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.Sha512DigestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * data struct:
 * active_user_token_eyJhbGciOiJIUzI1NiIsInR5cCI6
 *          - status: active/refreshed
 *          - secure: ccf0a763e1c0dc55d29
 *          -
 */
@Service
@RequiredArgsConstructor
public class TokenService {

    private static final String ENCRYPT_KEY = "ccf0a763e1c0dc55d29ef500fd4d43abebb24690ca2764476a68ea15c6b5d553"; //sha256
    public static final String TOKEN_VERSION = "1.1";

    private final SecurityProperties securityProperties;

    private final TokenStore tokenStore = new InMemoryTokenStore();

    /**
     * 签发
     */
    public String sign(User user) {
        String username = user.getUsername();
        Collection<GrantedAuthority> authorities = user.getAuthorities();


        final Map<String, String> payload = Map.of(
                SecurityConst.PAYLOAD_USER_NAME, username,
                SecurityConst.PAYLOAD_AUTHORITIES, authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")),
                SecurityConst.PAYLOAD_VER, TOKEN_VERSION
        );

        // https://www.dariawan.com/tutorials/java/java-localdatetime-tutorial-examples/
        var expireTime = Instant.now().plus(Duration.ofMinutes(securityProperties.getAccessExpire()));

        String sign = JwtUtil.sign(payload, encrypt(username), expireTime);

        tokenStore.put(sign, Map.of(
                "status", "active",
                "secure", encrypt(username)));

        return sign;
    }


    /**
     * 阻止
     *
     * @param token  Token
     */
    public void block(String token) {
        final var expTime = JwtUtil.getExpTime(token);
        // 如果当前时间小于过期时间 执行block逻辑
        if (expTime.isBefore(Instant.now())) {
            tokenStore.remove(token);
        }
    }

    /**
     * 判断是否被block了
     *
     * @param token token
     * @return true: 是被block了
     */
    public boolean isBlock(String token) {
        return !tokenStore.exist(token);
    }

    /**
     * 验证
     */
    public boolean verify(String token) {

        // 判断是否被block了
        if (isBlock(token)) return false;

        final String userId = JwtUtil.getClaim(token, SecurityConst.PAYLOAD_USER_NAME);
        final String secret = encrypt(userId);

        return JwtUtil.verify(token, secret);
    }

    /**
     * 查询在线的用户Token列表
     */
    public Set<String> activeTokens() {
        return activeUserTokenList();
    }


    // 一种稳定的加密
    private String encrypt(String userId) {
        return Sha512DigestUtils.shaHex(userId + ENCRYPT_KEY);
    }

    // 查询用户是否在线
    private boolean isActiveUser(String token) {
        return tokenStore.exist(token);
    }

    // 查询在线的 Token 列表
    private Set<String> activeUserTokenList() {
        return tokenStore.list().map(TokenDetail::token).collect(Collectors.toSet());
    }
}
