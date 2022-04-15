package com.example.springboot3security.configuration;

import com.example.springboot3security.security.filter.LoginFilter;
import com.example.springboot3security.security.filter.TokenAuthenticationFilter;
import com.example.springboot3security.service.TokenService;
import com.example.springboot3security.security.handler.CommonAccessDeniedHandler;
import com.example.springboot3security.security.handler.CommonAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

// TODO FIX Deprecated
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@RequiredArgsConstructor
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    private final AuthenticationManager authenticationManager;

    //配置认证请求
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] permitList = new String[]{
                "/login"
        };

        http
                .csrf().disable()
                .cors().disable()

                // 所有的请求除了允许的都需要认证
                .authorizeRequests()
                .antMatchers(permitList).permitAll()
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

    }

    /**
     * Details omitted for brevity
     */


    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(userDetailsService, tokenService);
    }

    public LoginFilter loginFilter() {
        return new LoginFilter(authenticationManager, tokenService);
    }
}
