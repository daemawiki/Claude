package com.daemawiki.daemawiki.domain.user.usecase;

import reactor.core.publisher.Mono;

public interface UserRegisterUseCase {
    Mono<Void> register();

}
