package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.model.UserModel;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class UserAbstractRepository implements UserRepository {
    @Override
    public Mono<UserModel> save(UserEntity entity) {
        return userMongoRepository.save(entity)
                .map(UserModel.class::cast);
    }

    @Override
    public Mono<UserModel> findByEmail(String email) {
        return userMongoRepository.findByEmail(email)
                .map(UserModel.class::cast);
    }

    @Override
    public Mono<Boolean> existsByEmail(String email) {
        return userMongoRepository.existsByEmail(email);
    }

    private final UserMongoRepository userMongoRepository;
}
