package com.daemawiki.daemawiki.security.session.repository;

import com.daemawiki.daemawiki.security.session.model.SessionModel;
import reactor.core.publisher.Mono;

public interface SessionRepository {
    Mono<SessionModel> save(SessionModel session);
    Mono<SessionModel> findBySessionIdAndIp(String sessionId, String ip);
    Mono<Void> deleteById(String sessionId, String ip);
}
