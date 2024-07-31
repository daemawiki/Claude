package com.daemawiki.daemawiki.global.security.session.repository;

import com.daemawiki.daemawiki.global.security.session.model.SessionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AbstractSessionRepository implements SessionRepository {

    @Override
    public Mono<SessionEntity> save(SessionEntity entity) {
        return sessionMongoRepository.save(entity);
    }

    @Override
    public Mono<SessionEntity> findBySessionIdAndIp(String sessionId, String ip) {
        return sessionMongoRepository.findByIdAndIp(sessionId, ip);
    }

    @Override
    public Mono<Void> deleteById(String sessionId) {
        return sessionMongoRepository.deleteById(sessionId);
    }

    private final SessionMongoRepository sessionMongoRepository;
}
