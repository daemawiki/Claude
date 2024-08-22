package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
class DocumentRepositoryImpl implements DocumentRepository {
    @Override
    public Mono<DocumentModel> save(DocumentModel model) {
        var mono = documentMongoRepository.save(DocumentEntityMapper.toEntity(model));

        return toModel(mono);
    }

    @Override
    public Mono<DocumentModel> findById(String id) {
        var mono = documentMongoRepository.findById(id);

        return toModel(mono);
    }

    @Override
    public Mono<DocumentModel> getRandom() {
        var mono = documentMongoRepository.getRandomDocument();

        return toModel(mono);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return documentMongoRepository.deleteById(id);
    }

    @Override
    public Flux<DocumentModel> search(String searchText, PagingInfo pagingInfo) {
        var flux = documentMongoRepository.search(
                searchText,
                pagingInfo.sortBy().getPath(),
                pagingInfo.sortDirection(),
                pagingInfo.page() * pagingInfo.size(),
                pagingInfo.size()
        );

        return toModel(flux);
    }

    private Mono<DocumentModel> toModel(Mono<DocumentEntity> mono) {
        return mono.map(DocumentEntityMapper::toModel);
    }

    private Flux<DocumentModel> toModel(Flux<DocumentEntity> flux) {
        return flux.map(DocumentEntityMapper::toModel);
    }

    private final DocumentMongoRepository documentMongoRepository;
}
