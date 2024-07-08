package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DocumentMongoRepository extends ReactiveMongoRepository<DocumentEntity, String> {
    @Aggregation({
            "{ '$project': { '_id': 1 } }",
            "{ '$sample': { 'size': 1 } }"
    })
    Mono<String> getRandomDocumentId();
}
