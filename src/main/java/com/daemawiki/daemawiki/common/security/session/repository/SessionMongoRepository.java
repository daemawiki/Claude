package com.daemawiki.daemawiki.common.security.session.repository;

import com.daemawiki.daemawiki.common.security.session.model.SessionEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface SessionMongoRepository extends ReactiveMongoRepository<SessionEntity, String> {
    Mono<SessionEntity> findByIdAndIp(String sessionId, String ip);
}
