package com.daemawiki.daemawiki.infrastructure.redis.storage;

import reactor.core.publisher.Mono;

import java.time.Duration;

public interface RedisOperation {
    Mono<String> getValue(String key);
    Mono<Boolean> hasKey(String key);
    Mono<Long> delete(String key);
    Mono<Boolean> save(String key, String value, Duration expiration);

}
