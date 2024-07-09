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

    @Override
    public Mono<DocumentEntity> findById(String id) {
        return documentMongoRepository.findById(id);
    }

    @Override
    public Mono<DocumentEntity> getRandom() {
        return documentMongoRepository.getRandomDocument();
    }

    private final DocumentMongoRepository documentMongoRepository;
}
