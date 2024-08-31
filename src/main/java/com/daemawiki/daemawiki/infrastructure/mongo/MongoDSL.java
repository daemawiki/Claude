package com.daemawiki.daemawiki.infrastructure.mongo;

import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class MongoDSL {
    private final Query query;

    private MongoDSL() {
        this.query = new Query();
    }

    public static MongoDSL createQuery() {
        return new MongoDSL();
    }

    public MongoDSL where(String field, Object value) {
        if (value != null) {
            query.addCriteria(Criteria.where(field).is(value));
        }
        return this;
    }

    public MongoDSL paging(PagingInfo pagingInfo) {
        if (pagingInfo.sortBy() != null) {
            Sort.Direction direction = pagingInfo.sortDirection() == 1 ? Sort.Direction.ASC : Sort.Direction.DESC;
            query.with(Sort.by(direction, pagingInfo.sortBy().getPath()));
        }
        return this;
    }

    public Query build() {
        return query;
    }
}
