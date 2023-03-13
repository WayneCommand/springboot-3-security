package com.example.springboot3security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Jwt 在 springboot application.yml 中的配置文件
 *
 * @author Felordcn
 * @since 15 :06 2019/10/25
 */
@Data
@Component
@ConfigurationProperties(prefix = SecurityProperties.SECURITY_PREFIX)
public class SecurityProperties {
    static final String SECURITY_PREFIX = "security.config";
    /**
     * enable
     */
    private Boolean enable;
    /**
     * access jwt token 有效时间 (分钟)
     */
    private Integer accessExpire = 24 * 60; // 6H
    /**
     * ignore paths
     */
    private List<String> ignorePaths;
}