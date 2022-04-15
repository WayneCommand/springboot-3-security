package com.example.springboot3security.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<ErrResp> exceptionHandler(RuntimeException e) {

        // ignore spring security exception.
        if (e instanceof AccessDeniedException)
            throw e;

        return ResponseEntity.internalServerError()
                .body(ErrResp.create(500L, e.getMessage()));
    }

    public record ErrResp(
            Instant timestamp,
            Long status,
            String error
    ) {
        public static ErrResp create(String error) {
            return new ErrResp(Instant.now(), 500L, error);
        }

        public static ErrResp create(Long status, String error) {
            return new ErrResp(Instant.now(), status, error);
        }
    }


}
