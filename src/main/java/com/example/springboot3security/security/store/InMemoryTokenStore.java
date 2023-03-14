package com.example.springboot3security.security.store;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class InMemoryTokenStore implements TokenStore {

    Map<String, Map<String, String>> store = new HashMap<>();


    @Override
    public void put(String token, Map<String, String> metadata) {
        store.put(token, metadata);
    }

    @Override
    public void remove(String token) {
        if (exist(token)) {
            store.remove(token);
        }
    }

    @Override
    public Stream<TokenDetail> list() {
        return store.keySet().stream()
                .map(k -> new TokenDetail(k, store.get(k)));
    }

    @Override
    public TokenDetail get(String token) {
        if (exist(token)) {
            return new TokenDetail(token, store.get(token));
        }
        return null;
    }

    @Override
    public boolean exist(String token) {
        return store.containsKey(token);
    }
}
