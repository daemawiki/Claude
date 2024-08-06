package com.daemawiki.daemawiki.common.security.session.repository.impl;

import com.daemawiki.daemawiki.common.security.session.model.SessionModel;
import com.daemawiki.daemawiki.common.security.session.repository.SessionRepository;
import com.daemawiki.daemawiki.infrastructure.redis.RedisKey;
import com.daemawiki.daemawiki.infrastructure.redis.storage.RedisOperation;
import jakarta.mail.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Repository
@RequiredArgsConstructor
public class SessionRepositoryImpl implements SessionRepository {

    @Override
    public Mono<SessionModel> save(SessionModel session) {
        return redisOperation.save(
                (RedisKey.SESSION + session.id() + ":" + session.ip()),
                session.userName(),
                Duration.ofHours(3)
        ).thenReturn(session);
    }

    @Override
    public Mono<SessionModel> findBySessionIdAndIp(String sessionId, String ip) {
        return redisOperation.getValue(RedisKey.SESSION + sessionId + ":" + ip)
                .map(value -> SessionModel.of(sessionId, ip, value));
    }

    @Override
    public Mono<Void> deleteById(String sessionId, String ip) {
        return redisOperation.delete(RedisKey.SESSION + sessionId + ":" + ip)
                .then();
    }

    private final RedisOperation redisOperation;
}
