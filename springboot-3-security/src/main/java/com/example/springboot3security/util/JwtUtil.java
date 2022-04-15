package com.example.springboot3security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.Map;

public class JwtUtil {

    // 默认过期时间
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    /**
     * 校验token是否正确
     *
     * @return 是否正确
     */
    public static boolean verify(String token, String secret) {
        try {
            //根据密码生成JWT效验器
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            //效验TOKEN
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     *
     * @return token中包含的claim
     */
    public static String getClaim(String token, String claim) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(claim).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名
     *
     * @param info      PAYLOAD
     * @param secret    SECRET
     * @return 加密的token
     */
    public static String sign(Map<String,String> info, String secret) {
        Date expire = new Date(System.currentTimeMillis() + EXPIRE_TIME);

        Algorithm algorithm = Algorithm.HMAC256(secret);

        final JWTCreator.Builder jwtBuilder = JWT.create();

        info.forEach(jwtBuilder::withClaim);

        return jwtBuilder.withExpiresAt(expire)
                .sign(algorithm);

    }

    /**
     * 获取过期时间
     * @param token
     * @return
     */
    public static Date getExpTime(String token) {
        final DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt();
    }


    // for test.
    public static void main(String[] args) {
        final String sign = sign(Map.of("user", "spring"), "boot");
        System.out.println(sign);
    }
}
