package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
class DocumentRepositoryImpl implements DocumentRepository {
    private final DocumentMongoRepository documentMongoRepository;
    private final DocumentEntityMapper documentEntityMapper;

    @Override
    public Mono<DocumentModel> save(DocumentModel model) {
        var mono = documentMongoRepository.save(documentEntityMapper.toEntity(model));

        return documentEntityMapper.toMonoModel(mono);
    }

    @Override
    public Mono<DocumentModel> findById(String id) {
        var mono = documentMongoRepository.findById(id);

        return documentEntityMapper.toMonoModel(mono);
    }

    @Override
    public Mono<DocumentModel> getRandom() {
        var mono = documentMongoRepository.getRandomDocument();

        return documentEntityMapper.toMonoModel(mono);
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

        return documentEntityMapper.toFluxModel(flux);
    }
}
