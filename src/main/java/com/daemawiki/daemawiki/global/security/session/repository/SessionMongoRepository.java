package com.daemawiki.daemawiki.global.security.session.repository;

import com.daemawiki.daemawiki.global.security.session.model.SessionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SessionMongoRepository extends ReactiveMongoRepository<SessionEntity, String> {
    Mono<SessionEntity> findByIdAndIp(String sessionId, String ip);
}
