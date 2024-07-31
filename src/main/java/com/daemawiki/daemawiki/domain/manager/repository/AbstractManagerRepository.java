package com.daemawiki.daemawiki.domain.manager.repository;

import com.daemawiki.daemawiki.domain.manager.model.ManagerEntity;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class AbstractManagerRepository implements ManagerRepository {

    @Override
    public Mono<ManagerEntity> save(ManagerEntity entity) {
        return managerMongoRepository.save(entity);
    }

    @Override
    public Flux<ManagerEntity> findAll() {
        return managerMongoRepository.findAll();
    }

    @Override
    public Mono<ManagerEntity> findByEmail(String email) {
        return managerMongoRepository.findByEmail(email);
    }

    @Override
    public Mono<Void> delete(ManagerEntity entity) {
        return managerMongoRepository.delete(entity);
    }

    private final ManagerMongoRepository managerMongoRepository;
}
