package com.daemawiki.daemawiki.domain.mail.repository.impl;

import com.daemawiki.daemawiki.common.error.customs.WrongRedisConnectionException;
import com.daemawiki.daemawiki.domain.mail.repository.AuthUserRepository;
import com.daemawiki.daemawiki.infrastructure.redis.RedisKey;
import com.daemawiki.daemawiki.infrastructure.redis.storage.RedisOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "인증된 유저 레디스 레포지토리")
class AuthUserRepositoryImpl implements AuthUserRepository {
    private static final String AUTH_MAIL = RedisKey.AUTH_USER.getKey();

    private final RedisOperation redisOperation;

    @Override
    public Mono<Boolean> save(String mail) {
        return handleError(redisOperation.save(
                AUTH_MAIL + mail,
                mail,
                Duration.ofHours(1)
        ));
    }

    @Override
    public Mono<Boolean> existsByEmail(String mail) {
        return handleError(redisOperation.hasKey(AUTH_MAIL + mail));
    }

    @Override
    public Mono<Void> delete(String mail) {
        return handleError(redisOperation.delete(AUTH_MAIL + mail).then());
    }

    private static <T> Mono<T> handleError(Mono<T> mono) {
        return mono.onErrorMap(e -> {
            log.error("#- Error: " + e);
            return e instanceof RedisConnectionFailureException ? WrongRedisConnectionException.EXCEPTION : e;
        });
    }
}
