package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface UserMongoRepository extends ReactiveMongoRepository<UserEntity, String> {
    Mono<Boolean> existsByEmail(String email);
    Mono<UserEntity> findByEmail(String email);

}
