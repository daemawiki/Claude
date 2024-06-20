package com.daemawiki.daemawiki.domain.mail.auth_code.repository;

import com.daemawiki.daemawiki.domain.mail.auth_code.model.AuthCodeModel;
import com.daemawiki.daemawiki.global.utils.redis.RedisKey;
import com.daemawiki.daemawiki.global.utils.redis.RedisOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class AuthCodeRepositoryImpl implements AuthCodeRepository {
    @Override
    public Mono<Boolean> save(AuthCodeModel model) {
        return redisOperation.save(
                AUTH_CODE + model.email(),
                model.code(),
                Duration.ofHours(3)
        );
    }

    @Override
    public Mono<AuthCodeModel> findByMail(String email) {
        return redisOperation.getValue(AUTH_CODE + email)
                .map(code -> AuthCodeModel.of(email, code));
    }

    @Override
    public Mono<Void> delete(AuthCodeModel model) {
        return redisOperation.delete(AUTH_CODE + model.email());
    }

    private static final String AUTH_CODE = RedisKey.AUTH_CODE.getKey();
    private final RedisOperation redisOperation;
}
