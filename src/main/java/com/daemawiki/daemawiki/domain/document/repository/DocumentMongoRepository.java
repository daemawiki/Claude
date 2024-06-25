package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DocumentMongoRepository extends ReactiveMongoRepository<DocumentEntity, String> {
}
