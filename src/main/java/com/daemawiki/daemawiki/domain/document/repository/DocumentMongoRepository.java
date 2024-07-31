package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentMongoRepository extends ReactiveMongoRepository<DocumentEntity, String> {
    @Aggregation("{ '$sample': { 'size': 1 } }")
    Mono<DocumentEntity> getRandomDocument();

    @Aggregation(pipeline = {
            "{ $match: { $or: [ " +
            "  { 'title': { $regex: ?0, $options: 'i' } }, " +
            "  { 'contents.content': { $regex: ?0, $options: 'i' } } " +
            "] } }",
            "{ $sort: { ?1: ?2 } }",
            "{ $skip: ?3 }",
            "{ $limit: ?4 }"
    })
    Flux<DocumentEntity> search(String searchText, String sortBy, int sortDirection, int skip, int limit);
}
