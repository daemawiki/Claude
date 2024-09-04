package com.daemawiki.daemawiki.security.session.repository.impl;

import com.daemawiki.daemawiki.common.error.exception.CustomExceptionFactory;
import com.daemawiki.daemawiki.infrastructure.redis.RedisKey;
import com.daemawiki.daemawiki.infrastructure.redis.storage.RedisOperation;
import com.daemawiki.daemawiki.security.session.model.SessionModel;
import com.daemawiki.daemawiki.security.session.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
@Slf4j(topic = "세션 레디스 저장소")
class SessionRepositoryImpl implements SessionRepository {
    private static final Duration SESSION_EXPIRATION = Duration.ofHours(3);

    private final RedisOperation redisOperation;

    @Override
    public Mono<SessionModel> save(SessionModel session) {
        return handleError(redisOperation.save(
                (RedisKey.SESSION + session.id() + session.ip()),
                session.userName(),
                SESSION_EXPIRATION
        ).thenReturn(session));
    }

    @Override
    public Mono<SessionModel> findBySessionIdAndIp(String sessionId, String ip) {
        return handleError(redisOperation.getValue(RedisKey.SESSION + sessionId + ip)
                .map(value -> SessionModel.of(sessionId, ip, value)));
    }

    @Override
    public Mono<Void> deleteById(String sessionId, String ip) {
        return handleError(redisOperation.delete(RedisKey.SESSION + sessionId + ip)
                .then());
    }

    private static <T> Mono<T> handleError(Mono<T> mono) {
        return mono.onErrorMap(e -> {
            log.error("#- Error: " + e);
            return e instanceof RedisConnectionFailureException ?
                    CustomExceptionFactory.internalServerError(e.getMessage(), e) : e;
        });
    }
}
