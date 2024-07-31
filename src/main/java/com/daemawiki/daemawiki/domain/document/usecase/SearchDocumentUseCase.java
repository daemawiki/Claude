package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.SimpleDocumentResult;
import com.daemawiki.daemawiki.global.utils.paging.PagingRequest;
import com.daemawiki.daemawiki.global.utils.searching.SearchResponse;
import reactor.core.publisher.Mono;

public interface SearchDocumentUseCase {
    Mono<SearchResponse<SimpleDocumentResult>> searchDocument(String searchText, PagingRequest request);
}
