package com.daemawiki.daemawiki.domain.manager.repository;

import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepositoryImpl extends AbstractManagerRepository {
    public ManagerRepositoryImpl(ManagerMongoRepository managerMongoRepository) {
        super(managerMongoRepository);
    }
}
