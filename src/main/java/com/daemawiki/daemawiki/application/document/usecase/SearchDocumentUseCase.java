package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.domain.document.SimpleDocumentResult;
import com.daemawiki.daemawiki.common.util.paging.PagingRequest;
import com.daemawiki.daemawiki.common.util.searching.SearchResponse;
import reactor.core.publisher.Mono;

public interface SearchDocumentUseCase {
    Mono<SearchResponse<SimpleDocumentResult>> searchDocument(String searchText, PagingRequest request);
}
