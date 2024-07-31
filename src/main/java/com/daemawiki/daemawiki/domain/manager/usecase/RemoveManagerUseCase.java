package com.daemawiki.daemawiki.domain.manager.usecase;

import reactor.core.publisher.Mono;

public interface RemoveManagerUseCase {
    Mono<Void> remove(String email);
}
