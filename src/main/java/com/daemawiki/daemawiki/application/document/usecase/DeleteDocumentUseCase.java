package com.daemawiki.daemawiki.application.document.usecase;

import reactor.core.publisher.Mono;

public interface DeleteDocumentUseCase {
    Mono<Void> delete(String documentId);
}
