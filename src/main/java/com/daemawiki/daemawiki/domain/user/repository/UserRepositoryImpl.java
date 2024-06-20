package com.daemawiki.daemawiki.domain.user.repository;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.global.utils.MongoQueryUtils;
import com.daemawiki.daemawiki.global.utils.PagingInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public class UserRepositoryImpl extends UserAbstractRepository {
    @Override
    public Flux<UserEntity> findByGenerationAndMajor(Integer generation, String major, PagingInfo pagingInfo) {
        return mongoQueryUtils.find(
                findByGenerationAndMajorQuery(generation, major, pagingInfo),
                UserEntity.class
        );
    }

    private Query findByGenerationAndMajorQuery(Integer generation, String major, PagingInfo pagingInfo) {
        Query query = new Query();

        Optional.ofNullable(generation)
                .ifPresent(gen -> query.addCriteria(Criteria.where("userInfo.gen").is(gen)));

        if (isNotBlank(major)){
            query.addCriteria(Criteria.where("userInfo.major").is(major));
        }

        Optional.ofNullable(pagingInfo.sortBy())
                .ifPresent(sortBy -> {
                    Sort.Direction direction = pagingInfo.sortDirection() == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
                    query.with(Sort.by(direction, sortBy));
                });

        return query;
    }

    private boolean isNotBlank(String str) {
        return !str.isBlank();
    }

    private final MongoQueryUtils mongoQueryUtils;
    public UserRepositoryImpl(UserMongoRepository userMongoRepository, MongoQueryUtils mongoQueryUtils) {
        super(userMongoRepository);
        this.mongoQueryUtils = mongoQueryUtils;
    }
}
