package com.daemawiki.daemawiki.application.document.usecase;

import reactor.core.publisher.Mono;

public interface DocumentRemoveUseCase {
    Mono<Void> delete(String documentId);
}
