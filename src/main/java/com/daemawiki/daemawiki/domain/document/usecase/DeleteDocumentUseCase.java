package com.daemawiki.daemawiki.domain.document.usecase;

import reactor.core.publisher.Mono;

public interface DeleteDocumentUseCase {
    Mono<Void> delete(String documentId);
}
