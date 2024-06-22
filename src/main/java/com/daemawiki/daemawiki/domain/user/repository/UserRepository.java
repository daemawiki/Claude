package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.model.UserModel;
import com.daemawiki.daemawiki.global.utils.PagingInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<UserModel> save(UserEntity entity);
    Mono<UserModel> findByEmail(String email);
    Flux<UserModel> findByGenerationAndMajor(Integer generation, String major, PagingInfo pagingInfo);

    Mono<Boolean> existsByEmail(String email);
}
