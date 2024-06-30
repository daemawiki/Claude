package com.daemawiki.daemawiki.global.utils.redis;

import reactor.core.publisher.Mono;

import java.time.Duration;

public interface RedisOperation {
    Mono<String> getValue(String key);
    Mono<Boolean> hasKey(String key);
    Mono<Void> delete(String key);
    Mono<Boolean> save(String key, String value, Duration expiration);

}
