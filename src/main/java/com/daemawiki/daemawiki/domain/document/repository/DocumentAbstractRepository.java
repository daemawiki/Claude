package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class DocumentAbstractRepository implements DocumentRepository {
    @Override
    public Mono<DocumentEntity> save(DocumentEntity entity) {
        return documentMongoRepository.save(entity);
    }

    private final DocumentMongoRepository documentMongoRepository;
}
