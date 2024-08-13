package com.daemawiki.daemawiki.application.document;

import reactor.core.publisher.Mono;

public interface DeleteDocumentUseCase {
    Mono<Void> delete(String documentId);
}
