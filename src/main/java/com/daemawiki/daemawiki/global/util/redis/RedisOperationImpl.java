package com.daemawiki.daemawiki.global.util.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class RedisOperationImpl implements RedisOperation {
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

    private final ReactiveRedisOperations<String, String> redisOperations;
}

