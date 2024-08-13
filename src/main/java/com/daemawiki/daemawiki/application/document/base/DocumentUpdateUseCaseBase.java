package com.daemawiki.daemawiki.application.document.base;

import reactor.core.publisher.Mono;

public interface DocumentUpdateUseCaseBase<T> {
    Mono<Void> update(String documentId, T updateData);
}
