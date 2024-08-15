package com.daemawiki.daemawiki.domain.manager.repository;

import org.springframework.stereotype.Repository;

@Repository
class ManagerRepositoryImpl extends AbstractManagerRepository {
    public ManagerRepositoryImpl(ManagerMongoRepository managerMongoRepository) {
        super(managerMongoRepository);
    }
}
