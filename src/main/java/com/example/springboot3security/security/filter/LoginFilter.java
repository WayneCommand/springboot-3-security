package com.example.springboot3security.security.filter;

import com.example.springboot3security.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    /**
     * 登陆地址
     */
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");

    private final ObjectMapper json = JsonMapper.builder()
            .findAndAddModules()
            .build();


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 是否为登陆
        if (!DEFAULT_ANT_PATH_REQUEST_MATCHER.matches(request)){
            filterChain.doFilter(request, response);
        }else {
            Authentication authenticate = authenticationManager
                    .authenticate(
                            buildToken(request)
                    );


            // 拿出已认证的用户
            User user = (User) authenticate.getPrincipal();

            // sign token

            String jwt = tokenService.sign(user);

            // 返回 token
            writeToken(response, jwt);
        }

    }

    private AbstractAuthenticationToken buildToken(HttpServletRequest request) {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return new UsernamePasswordAuthenticationToken(
                username, password
        );
    }

    private void writeToken(HttpServletResponse response, String token) {
        try {
            write(response, json.writeValueAsString(
                    new Token(token)
            ));
        } catch (IOException ignored) {
        }
    }

    private void write(HttpServletResponse response, String content) throws IOException {
        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter()
                .write(content);
        response.flushBuffer();
    }

    record Token(String token) {
    }

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        String password = passwordEncoder.encode("password");

        System.out.println(password);
    }

}
