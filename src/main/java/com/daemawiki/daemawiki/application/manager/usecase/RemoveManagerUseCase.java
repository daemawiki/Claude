package com.daemawiki.daemawiki.application.manager.usecase;

import reactor.core.publisher.Mono;

public interface RemoveManagerUseCase {
    Mono<Void> remove(String email);
}
