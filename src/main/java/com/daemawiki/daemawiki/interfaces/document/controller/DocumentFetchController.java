package com.daemawiki.daemawiki.interfaces.document.controller;

import com.daemawiki.daemawiki.application.document.usecase.DocumentFetchUseCase;
import com.daemawiki.daemawiki.common.annotation.ui.DocumentApi;
import com.daemawiki.daemawiki.common.util.paging.PagingRequest;
import com.daemawiki.daemawiki.common.util.searching.SearchResponse;
import com.daemawiki.daemawiki.domain.document.DocumentSimpleResult;
import com.daemawiki.daemawiki.interfaces.document.dto.response.DocumentFullResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@DocumentApi
@RequiredArgsConstructor
class DocumentFetchController {
    private final DocumentFetchUseCase fetchUseCase;

    @GetMapping("/random")
    Mono<DocumentFullResponse> fetchRandom() {
        return fetchUseCase.fetchRandom();
    }

    @GetMapping("/search")
    Mono<SearchResponse<DocumentSimpleResult>> search(
            @RequestParam String text,
            @ModelAttribute @Valid PagingRequest request
    ) {
        return fetchUseCase.search(text, request);
    }

    @GetMapping("/{documentId}")
    Mono<DocumentFullResponse> fetchById(
            @PathVariable String documentId
    ) {
        return fetchUseCase.fetchById(documentId);
    }
}
