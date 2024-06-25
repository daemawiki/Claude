package com.daemawiki.daemawiki.domain.document.repository;

import org.springframework.stereotype.Repository;

@Repository
public class DocumentRepositoryImpl extends DocumentAbstractRepository {
    public DocumentRepositoryImpl(DocumentMongoRepository documentMongoRepository) {
        super(documentMongoRepository);
    }
}
