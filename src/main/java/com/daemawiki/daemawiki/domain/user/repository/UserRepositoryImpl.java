package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.global.util.mongo.MongoQueryUtils;
import com.daemawiki.daemawiki.global.util.paging.PagingInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

@Repository
public class UserRepositoryImpl extends UserAbstractRepository {
    @Override
    public Flux<UserEntity> findByGenerationAndMajor(Integer generation, String major, PagingInfo pagingInfo) {
        return mongoQueryUtils.find(
                buildQuery(generation, major, pagingInfo),
                UserEntity.class
        );
    }

    private Query buildQuery(Integer generation, String major, PagingInfo pagingInfo) {
        Query query = new Query();

        if (generation != null) {
            query.addCriteria(Criteria.where("userInfo.generation").is(generation));
        }

        if (StringUtils.hasText(major)) {
            query.addCriteria(Criteria.where("userInfo.major").is(major));
        }

        if (pagingInfo.sortBy() != null) {
            Sort.Direction direction = pagingInfo.sortDirection() == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
            query.with(Sort.by(direction, pagingInfo.sortBy().getPath()));
        }

        return query;
    }

    private final MongoQueryUtils mongoQueryUtils;

    public UserRepositoryImpl(UserMongoRepository userMongoRepository, MongoQueryUtils mongoQueryUtils) {
        super(userMongoRepository);
        this.mongoQueryUtils = mongoQueryUtils;
    }
}
