package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.DocumentFetchUseCase;
import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import com.daemawiki.daemawiki.common.util.paging.PagingRequest;
import com.daemawiki.daemawiki.common.util.searching.SearchResponse;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.DocumentSimpleResult;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
class DocumentFetchService implements DocumentFetchUseCase {


    @Override
    public Mono<FullDocumentResponse> fetchById(String documentId) {
        return documentRepository.findById(documentId)
                .switchIfEmpty(Mono.error(new RuntimeException())) // 문서 id로 찾지 못했을 때
                .map(FullDocumentResponse::createNewInstanceFromDocumentModel);
    }

    @Override
    public Mono<FullDocumentResponse> fetchRandom() {
        return documentRepository.getRandom()
                .map(FullDocumentResponse::createNewInstanceFromDocumentModel);
    }

    @Override
    public Mono<SearchResponse<DocumentSimpleResult>> search(String searchText, PagingRequest request) {
        return documentRepository.search(searchText, PagingInfo.fromPagingRequest(request))
                .map(DocumentSimpleResult::fromDocumentModel)
                .collectList()
                .map(result -> createSearchResponse(result, request.page(), request.size()));
    }

    private SearchResponse<DocumentSimpleResult> createSearchResponse(List<DocumentSimpleResult> result, int requestPage, int requestSize) {
        var totalElements = result.size();
        final var hasNext = totalElements > requestSize;

        List<DocumentSimpleResult> finalResult;
        if (hasNext) {
            finalResult = result.subList(0, totalElements - 1);
        } else {
            finalResult = result;
        }

        return SearchResponse.of(finalResult, finalResult.size(), requestPage, hasNext);
    }

    private final DocumentRepository documentRepository;
}
