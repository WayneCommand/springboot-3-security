package com.example.springboot3security.security.store;

import java.util.Map;

public record TokenDetail(
        String token,
        Map<String, String> metadata) {


}
