package com.example.springboot3security.configuration;

import com.example.springboot3security.security.CommonPermissionEvaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class CommonMethodSecurityConfiguration {

    // https://docs.spring.io/spring-security/reference/servlet/authorization/method-security.html
    @Bean
    static MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler =
                new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(new CommonPermissionEvaluator());

        return expressionHandler;
    }
}