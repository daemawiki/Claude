package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DocumentMongoRepository extends ReactiveMongoRepository<DocumentEntity, String> {
    @Aggregation("{ '$sample': { 'size': 1 } }")
    Mono<DocumentEntity> getRandomDocument();
}
