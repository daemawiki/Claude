package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.global.utils.paging.PagingInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentRepository {
    Mono<DocumentEntity> save(DocumentEntity entity);
    Mono<DocumentEntity> increaseView(DocumentEntity entity);
    Mono<DocumentEntity> findById(String id);
    Mono<DocumentEntity> getRandom();
    Mono<Void> deleteById(String id);
    Flux<DocumentEntity> search(String searchText, PagingInfo pagingInfo);
}
