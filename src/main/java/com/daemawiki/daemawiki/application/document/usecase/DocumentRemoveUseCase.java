package com.daemawiki.daemawiki.application.document.usecase;

import reactor.core.publisher.Mono;

public interface DocumentRemoveUseCase {
    Mono<Void> remove(String documentId);
}
