package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import reactor.core.publisher.Mono;

public interface DocumentRepository {
    Mono<DocumentEntity> save(DocumentEntity entity);
    Mono<DocumentEntity> increaseView(DocumentEntity entity);
    Mono<DocumentEntity> findById(String id);
    Mono<String> getRandom();
}
