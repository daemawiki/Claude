package com.daemawiki.daemawiki.infrastructure.redis.storage.impl;

import com.daemawiki.daemawiki.infrastructure.redis.storage.RedisOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
class RedisOperationImpl implements RedisOperation {
    private final ReactiveRedisOperations<String, String> redisOperations;

    @Override
    public Mono<String> getValue(String key) {
        return redisOperations.opsForValue().get(key);
    }

    @Override
    public Mono<Boolean> hasKey(String key) {
        return redisOperations.hasKey(key);
    }

    @Override
    public Mono<Long> delete(String key) {
        return redisOperations.delete(key);
    }

    @Override
    public Mono<Boolean> save(String key, String value, Duration expiration) {
        return redisOperations.opsForValue().set(key, value, expiration);
    }
}

