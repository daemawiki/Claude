package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class UserAbstractRepository implements UserRepository {
    @Override
    public Mono<UserEntity> save(UserEntity entity) {
        return userMongoRepository.save(entity);
    }

    @Override
    public Mono<UserEntity> findByEmail(String email) {
        return userMongoRepository.findByEmail(email);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userMongoRepository.existsByEmail(email);
    }

    private final UserMongoRepository userMongoRepository;
}
