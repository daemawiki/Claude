package com.daemawiki.daemawiki.domain.mail.auth_code.repository;

import com.daemawiki.daemawiki.domain.mail.auth_code.model.AuthCodeModel;
import com.daemawiki.daemawiki.global.error.customs.WrongRedisConnectionException;
import com.daemawiki.daemawiki.global.utils.redis.RedisKey;
import com.daemawiki.daemawiki.global.utils.redis.RedisOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "인증 코드 레포지토리")
public class AuthCodeRepositoryImpl implements AuthCodeRepository {
    @Override
    public Mono<Boolean> save(AuthCodeModel model) {
        return handleError(redisOperation.save(
                AUTH_CODE + model.email(),
                model.code(),
                Duration.ofMinutes(30)
        ));
    }

    @Override
    public Mono<AuthCodeModel> findByMail(String email) {
        return handleError(redisOperation.getValue(AUTH_CODE + email)
                .map(code -> AuthCodeModel.of(email, code)));
    }

    @Override
    public Mono<Void> delete(AuthCodeModel model) {
        return deleteByEmail(model.email());
    }

    @Override
    public Mono<Long> deleteByEmail(String email) {
        return handleError(redisOperation.delete(AUTH_CODE + email));
    }
    private static <T> Mono<T> handleError(Mono<T> mono) {
        return mono.onErrorMap(e -> e instanceof RedisConnectionFailureException ? WrongRedisConnectionException.EXCEPTION : e);
    }

    private static final String AUTH_CODE = RedisKey.AUTH_CODE.getKey();
    private final RedisOperation redisOperation;
}
