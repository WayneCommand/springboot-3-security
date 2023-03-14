package com.example.springboot3security.configuration;

import com.example.springboot3security.security.filter.LoginFilter;
import com.example.springboot3security.security.filter.TokenAuthenticationFilter;
import com.example.springboot3security.service.TokenService;
import com.example.springboot3security.security.handler.CommonAccessDeniedHandler;
import com.example.springboot3security.security.handler.CommonAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final TokenService tokenService;
    private final String[] defaultPermitList = Stream.of(
            "/actuator/**"
    ).toArray(String[]::new);


    //配置认证请求
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        String[] permitList = new String[]{
                "/login"
        };


        http
                .csrf().disable()
                .cors().disable()

                // 所有的请求除了允许的都需要认证
                .authorizeHttpRequests()
                .requestMatchers(permitList).permitAll()
                .requestMatchers(defaultPermitList).permitAll()
                .anyRequest().authenticated()

                // 无状态session
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 异常处理
                .and()
                .exceptionHandling()
                // 没有权限
                .accessDeniedHandler(new CommonAccessDeniedHandler())
                // 没有认证通过
                .authenticationEntryPoint(new CommonAuthenticationEntryPoint())

                // filters
                .and()
                .addFilterAfter(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(loginFilter(authenticationManager), TokenAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
        return authenticationConfiguration.getAuthenticationManager();
    }

    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenService);
    }

    public LoginFilter loginFilter(AuthenticationManager authenticationManager) {
        return new LoginFilter(authenticationManager, tokenService);
    }
}
