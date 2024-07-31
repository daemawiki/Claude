package com.daemawiki.daemawiki.domain.manager.repository;

import com.daemawiki.daemawiki.domain.manager.model.ManagerEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ManagerRepository {
    Mono<ManagerEntity> save(ManagerEntity entity);
    Flux<ManagerEntity> findAll();
    Mono<ManagerEntity> findByEmail(String email);
    Mono<Void> delete(ManagerEntity entity);
}
