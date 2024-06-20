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

    private final UserMongoRepository userMongoRepository;
}
