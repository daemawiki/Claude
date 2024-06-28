package com.daemawiki.daemawiki.domain.mail.auth_user.repository;

import com.daemawiki.daemawiki.domain.mail.auth_user.model.AuthUserModel;
import com.daemawiki.daemawiki.global.utils.redis.RedisKey;
import com.daemawiki.daemawiki.global.utils.redis.RedisOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class AuthUserRepositoryImpl implements AuthUserRepository {
    @Override
    public Mono<Boolean> save(String mail) {
        return redisOperation.save(
                AUTH_MAIL + mail,
                mail,
                Duration.ofHours(1)
        );
    }

    @Override
    public Mono<Boolean> findByMail(String mail) {
        return redisOperation.getValue(AUTH_MAIL + mail)
                .hasElement();
    }

    @Override
    public Mono<Void> delete(String mail) {
        return redisOperation.delete(AUTH_MAIL + mail);
    }

    private static final String AUTH_MAIL = RedisKey.AUTH_USER.getKey();
    private final RedisOperation redisOperation;
}
