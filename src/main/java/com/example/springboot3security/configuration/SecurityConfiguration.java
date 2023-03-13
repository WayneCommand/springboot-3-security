package com.example.springboot3security.configuration;

import com.example.springboot3security.security.filter.LoginFilter;
import com.example.springboot3security.security.filter.TokenAuthenticationFilter;
import com.example.springboot3security.service.TokenService;
import com.example.springboot3security.security.handler.CommonAccessDeniedHandler;
import com.example.springboot3security.security.handler.CommonAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
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
public class SecurityConfiguration {

    private final TokenService tokenService;

    private final AuthenticationManager authenticationManager;
    private final String[] defaultPermitList = Stream.of(
            "/actuator/**"
    ).toArray(String[]::new);

    public SecurityConfiguration(@NonNull TokenService tokenService, @NonNull AuthenticationConfiguration authenticationConfiguration) throws Exception {
        this.tokenService = tokenService;

        // https://stackoverflow.com/questions/72381114/spring-security-upgrading-the-deprecated-websecurityconfigureradapter-in-spring
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
    }


    //配置认证请求
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
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
                .addFilterAfter(loginFilter(), TokenAuthenticationFilter.class);
        return http.build();
    }


    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenService);
    }

    public LoginFilter loginFilter() {
        return new LoginFilter(authenticationManager, tokenService);
    }
}
