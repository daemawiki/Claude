package com.daemawiki.daemawiki.infrastructure.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "redis")
public record RedisProperties(
        String host,
        int port
) {
}