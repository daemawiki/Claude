package com.daemawiki.daemawiki.global.security.session.repository;

import com.daemawiki.daemawiki.global.security.session.model.SessionEntity;
import reactor.core.publisher.Mono;

public interface SessionRepository {
    Mono<SessionEntity> save(SessionEntity entity);
    Mono<SessionEntity> findBySessionIdAndIp(String sessionId, String ip);
    Mono<Void> deleteById(String sessionId);
}
