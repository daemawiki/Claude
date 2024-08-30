package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.common.util.paging.PagingRequest;
import com.daemawiki.daemawiki.common.util.searching.SearchResponse;
import com.daemawiki.daemawiki.domain.document.DocumentSimpleResult;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import reactor.core.publisher.Mono;

public interface DocumentFetchUseCase {
    Mono<FullDocumentResponse> fetchById(String documentId);
    Mono<FullDocumentResponse> fetchRandom();
    Mono<SearchResponse<DocumentSimpleResult>> search(String searchText, PagingRequest request);

}
