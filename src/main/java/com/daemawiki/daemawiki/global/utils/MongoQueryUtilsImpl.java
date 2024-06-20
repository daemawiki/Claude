package com.daemawiki.daemawiki.global.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@RequiredArgsConstructor
public class MongoQueryUtilsImpl implements MongoQueryUtils {
    public <T> Flux<T> find(Query query, Class<T> targetClass) {
        return reactiveMongoTemplate.find(
                query, targetClass
        );
    }

    private final ReactiveMongoTemplate reactiveMongoTemplate;
}