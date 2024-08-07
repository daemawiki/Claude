package com.daemawiki.daemawiki.domain.document.usecase.base;

import reactor.core.publisher.Mono;

public interface DocumentUpdateUseCaseBase<T> {
    Mono<Void> update(String documentId, T updateData);
}
