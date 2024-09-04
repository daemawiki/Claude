package com.daemawiki.daemawiki.domain.manager.repository;

import com.daemawiki.daemawiki.domain.manager.model.ManagerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
class ManagerRepositoryImpl implements ManagerRepository {
    private final ManagerMongoRepository managerMongoRepository;

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
}
