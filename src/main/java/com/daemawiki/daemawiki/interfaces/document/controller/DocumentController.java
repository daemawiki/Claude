package com.daemawiki.daemawiki.interfaces.document.controller;

import com.daemawiki.daemawiki.application.document.usecase.*;
import com.daemawiki.daemawiki.common.util.http.ListRequest;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import com.daemawiki.daemawiki.interfaces.document.dto.request.CreateDocumentRequest;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.DocumentSimpleResult;
import com.daemawiki.daemawiki.common.util.paging.PagingRequest;
import com.daemawiki.daemawiki.common.util.searching.SearchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/document")
class DocumentController {
    private final DocumentRemoveUseCase removeUseCase;
    private final DocumentCreateUseCase createUseCase;
    private final DocumentFetchUseCase fetchUseCase;
    private final DocumentEditUseCase editUseCase;

    @PatchMapping("/{documentId}/info")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> editInfo(
            @PathVariable String documentId,
            @RequestBody DocumentElementDtos.UpdateInfo request
    ) {
        return editUseCase.editInfo(documentId, request);
    }

    @PatchMapping("/{documentId}/content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> editContents(
            @PathVariable String documentId,
            @RequestBody ListRequest<DocumentElementDtos.Content> request
    ) {
        return editUseCase.editContents(documentId, request.list());
    }

    @GetMapping("/random")
    Mono<FullDocumentResponse> fetchRandom() {
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
    Mono<FullDocumentResponse> findOne(
            @PathVariable String documentId
    ) {
        return fetchUseCase.fetchById(documentId);
    }

    @DeleteMapping("/{documentId}")
    Mono<Void> remove(
            @PathVariable String documentId
    ) {
        return removeUseCase.remove(documentId);
    }

    @PostMapping
    Mono<Void> create(
            @RequestBody CreateDocumentRequest request
    ) {
        return createUseCase.create(request);
    }
}
