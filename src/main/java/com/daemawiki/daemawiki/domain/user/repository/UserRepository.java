package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.global.utils.paging.PagingInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<UserEntity> save(UserEntity entity);
    Mono<UserEntity> findByEmail(String email);
    Flux<UserEntity> findByGenerationAndMajor(Integer generation, String major, PagingInfo pagingInfo);

    Mono<Boolean> existsByEmail(String email);
}
