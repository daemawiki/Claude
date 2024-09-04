package com.daemawiki.daemawiki.infrastructure.mongo.impl;

import com.daemawiki.daemawiki.infrastructure.mongo.MongoQueryUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
class MongoQueryUtilsImpl implements MongoQueryUtils {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    @Override
    public <T> Flux<T> find(Query query, Class<T> targetClass) {
        return reactiveMongoTemplate.find(
                query, targetClass
        );
    }
}