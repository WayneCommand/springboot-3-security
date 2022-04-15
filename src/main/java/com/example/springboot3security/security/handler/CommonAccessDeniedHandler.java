package com.example.springboot3security.security.handler;

import com.example.springboot3security.configuration.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;

public class CommonAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper json = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {


        response.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter()
                .write(json.writeValueAsString(
                        GlobalExceptionHandler.ErrResp.create((long) HttpServletResponse.SC_FORBIDDEN, "Access Denied")
                ));
        response.flushBuffer();
    }
}
