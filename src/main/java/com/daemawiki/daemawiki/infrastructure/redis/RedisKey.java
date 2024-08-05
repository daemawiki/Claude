package com.daemawiki.daemawiki.infrastructure.redis;

public enum RedisKey {

    AUTH_USER("USER_"),
    AUTH_CODE("CODE_");

    private final String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}