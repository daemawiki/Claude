package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DocumentRepository {
    Mono<DocumentModel> save(DocumentModel model);
    Mono<DocumentModel> findById(String id);
    Mono<DocumentModel> getRandom();
    Mono<Void> deleteById(String id);
    Flux<DocumentModel> search(String searchText, PagingInfo pagingInfo);
}
