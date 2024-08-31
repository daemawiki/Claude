package com.daemawiki.daemawiki.application.manager.usecase;

import reactor.core.publisher.Mono;

public interface ManagerRemoveUseCase {
    Mono<Void> remove(String email);
}
