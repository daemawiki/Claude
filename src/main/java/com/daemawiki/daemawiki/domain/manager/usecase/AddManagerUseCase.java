package com.daemawiki.daemawiki.domain.manager.usecase;

import reactor.core.publisher.Mono;

public interface AddManagerUseCase {
    Mono<Void> add(String email);
}
