package com.daemawiki.daemawiki.common.security.session.repository;

import com.daemawiki.daemawiki.common.security.session.model.SessionEntity;
import reactor.core.publisher.Mono;

public interface SessionRepository {
    Mono<SessionEntity> save(SessionEntity entity);
    Mono<SessionEntity> findBySessionIdAndIp(String sessionId, String ip);
    Mono<Void> deleteById(String sessionId);
}
