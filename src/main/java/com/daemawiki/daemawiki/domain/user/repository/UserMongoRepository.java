package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface UserMongoRepository extends ReactiveMongoRepository<UserEntity, String> {
}
