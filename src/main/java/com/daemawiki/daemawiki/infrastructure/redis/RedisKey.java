package com.daemawiki.daemawiki.infrastructure.redis;

public enum RedisKey {

    AUTH_USER("USER-"),
    AUTH_CODE("CODE-"),
    SESSION("SESSION-");

    private final String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}