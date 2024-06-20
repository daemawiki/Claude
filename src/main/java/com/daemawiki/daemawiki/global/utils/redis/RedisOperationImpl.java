package com.daemawiki.daemawiki.global.utils.redis;

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
    public Mono<Void> delete(String key) {
        return redisOperations.delete(key)
                .then();
    }

    @Override
    public Mono<Boolean> save(String key, String value, Duration expiration) {
        return redisOperations.opsForValue().set(key, value, expiration);
    }

    private final ReactiveRedisOperations<String, String> redisOperations;
}

