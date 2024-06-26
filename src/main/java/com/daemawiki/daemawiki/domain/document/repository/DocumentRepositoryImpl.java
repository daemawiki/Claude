package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class DocumentRepositoryImpl extends DocumentAbstractRepository {
    @Override
    public Mono<DocumentEntity> increaseView(DocumentEntity entity) {
        return Mono.justOrEmpty(entity)
                .doOnNext(DocumentEntity::increaseView)
                .flatMap(this::save);
    }

    public DocumentRepositoryImpl(DocumentMongoRepository documentMongoRepository) {
        super(documentMongoRepository);
    }
}
