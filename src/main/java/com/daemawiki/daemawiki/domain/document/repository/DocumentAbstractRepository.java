package com.daemawiki.daemawiki.domain.document.repository;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public abstract class DocumentAbstractRepository implements DocumentRepository {
    @Override
    public Mono<DocumentEntity> save(DocumentEntity entity) {
        return documentMongoRepository.save(entity);
    }

    @Override
    public Mono<DocumentEntity> findById(String id) {
        return documentMongoRepository.findById(id);
    }

    @Override
    public Mono<DocumentEntity> getRandom() {
        return documentMongoRepository.getRandomDocument();
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return documentMongoRepository.deleteById(id);
    }

    @Override
    public Flux<DocumentEntity> search(String searchText, PagingInfo pagingInfo) {
        return documentMongoRepository.search(
                searchText,
                pagingInfo.sortBy().getPath(),
                pagingInfo.sortDirection(),
                pagingInfo.page() * pagingInfo.size(),
                pagingInfo.size()
        );
    }

    private final DocumentMongoRepository documentMongoRepository;
}
