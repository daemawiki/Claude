package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.SearchDocumentUseCase;
import com.daemawiki.daemawiki.domain.document.model.SimpleDocumentResult;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.common.util.paging.PagingInfo;
import com.daemawiki.daemawiki.common.util.paging.PagingRequest;
import com.daemawiki.daemawiki.common.util.searching.SearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchDocumentService implements SearchDocumentUseCase {

    private final DocumentRepository documentRepository;

    @Override
    public Mono<SearchResponse<SimpleDocumentResult>> searchDocument(String searchText, PagingRequest request) {
        return documentRepository.search(searchText, PagingInfo.fromPagingRequest(request))
                .map(SimpleDocumentResult::fromDocumentEntity)
                .collectList()
                .map(result -> createSearchResponse(result, request.page(), request.size()));
    }

    private SearchResponse<SimpleDocumentResult> createSearchResponse(List<SimpleDocumentResult> result, int requestPage, int requestSize) {
        int totalElements = result.size();
        boolean hasNext = totalElements > requestSize;

        List<SimpleDocumentResult> finalResult;
        if (hasNext) {
            finalResult = result.subList(0, totalElements - 1);
        } else {
            finalResult = result;
        }

        return SearchResponse.of(finalResult, finalResult.size(), requestPage, hasNext);
    }
}
