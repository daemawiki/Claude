package com.daemawiki.daemawiki.domain.user.component;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import reactor.core.publisher.Mono;

public interface CurrentUser {
    Mono<UserEntity> get();

}
