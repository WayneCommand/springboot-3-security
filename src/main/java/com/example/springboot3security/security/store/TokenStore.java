package com.example.springboot3security.security.store;


import java.util.Map;
import java.util.stream.Stream;

/**
 * Token 存储器接口
 */
public interface TokenStore {


    /**
     * 保存或替换一个Token
     * @param token Token
     * @param metadata 元数据
     */
    void put(String token, Map<String, String> metadata);

    /**
     * 移除一个Token
     * @param token Token
     */
    void remove(String token);

    /**
     * 列出所有Token（考虑合理性）
     */
    Stream<TokenDetail> list();

    /**
     * 获取Token和元数据
     * @param token Token
     */
    TokenDetail get(String token);

    /**
     * 判断是否存在
     * @param token Token
     * @return true：存在
     */
    boolean exist(String token);

}
