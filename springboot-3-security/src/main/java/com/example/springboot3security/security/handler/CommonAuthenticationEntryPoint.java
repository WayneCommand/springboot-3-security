package com.example.springboot3security.security.handler;

import com.example.springboot3security.configuration.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CommonAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper json = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("Security","Spring");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter()
                .write(json.writeValueAsString(
                        GlobalExceptionHandler.ErrResp.create((long) HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
                ));
        response.flushBuffer();
    }
}
