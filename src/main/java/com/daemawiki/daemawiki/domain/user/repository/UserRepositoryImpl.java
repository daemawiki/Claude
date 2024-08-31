package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.infrastructure.mongo.MongoDSL;
import com.daemawiki.daemawiki.infrastructure.mongo.MongoQueryUtils;
import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
class UserRepositoryImpl extends UserAbstractRepository {
    private final MongoQueryUtils mongoQueryUtils;

    public UserRepositoryImpl(UserMongoRepository userMongoRepository, MongoQueryUtils mongoQueryUtils) {
        super(userMongoRepository);
        this.mongoQueryUtils = mongoQueryUtils;
    }

    @Override
    public Flux<UserEntity> findByGenerationAndMajor(Integer generation, String major, PagingInfo pagingInfo) {
        return mongoQueryUtils.find(
                buildQuery(generation, major, pagingInfo),
                UserEntity.class
        );
    }

    private Query buildQuery(Integer generation, String major, PagingInfo pagingInfo) {
        return MongoDSL.createQuery()
                .where("userInfo.generation", generation)
                .where("userInfo.major", major)
                .paging(pagingInfo)
                .build();
    }
}
