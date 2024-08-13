package com.daemawiki.daemawiki.interfaces.document.controller;

import com.daemawiki.daemawiki.application.document.*;
import com.daemawiki.daemawiki.interfaces.document.dto.request.UpdateDocumentContentsRequest;
import com.daemawiki.daemawiki.interfaces.document.dto.request.CreateDocumentRequest;
import com.daemawiki.daemawiki.interfaces.document.dto.request.UpdateDocumentInfoAndCategoryRequest;
import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.model.SimpleDocumentResult;
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
public class DocumentController {

    @PatchMapping("/{documentId}/info")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateDocumentInfo(
            @PathVariable String documentId,
            @RequestBody UpdateDocumentInfoAndCategoryRequest request
    ) {
        return updateDocumentInfoUseCase.update(documentId, request);
    }

    @PatchMapping("/{documentId}/content")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> updateDocumentContents(
            @PathVariable String documentId,
            @RequestBody UpdateDocumentContentsRequest request
    ) {
        return updateDocumentContentsUseCase.update(documentId, request.contents());
    }

    @GetMapping("/random")
    public Mono<FullDocumentResponse> getRandomDocument() {
        return getRandomDocumentUseCase.get();
    }

    @GetMapping("/search")
    public Mono<SearchResponse<SimpleDocumentResult>> search(
            @RequestParam String text,
            @ModelAttribute @Valid PagingRequest request
    ) {
        return searchDocumentUseCase.searchDocument(text, request);
    }

    @GetMapping("/{documentId}")
    public Mono<FullDocumentResponse> findOne(
            @PathVariable String documentId
    ) {
        return findOneDocumentUseCase.findById(documentId);
    }

    @DeleteMapping("/{documentId}")
    public Mono<Void> delete(
            @PathVariable String documentId
    ) {
        return deleteDocumentUseCase.delete(documentId);
    }

    @PostMapping()
    public Mono<Void> create(
            @RequestBody CreateDocumentRequest request
    ) {
        return createDocumentUseCase.create(request);
    }

    private final UpdateDocumentContentsUseCase updateDocumentContentsUseCase;
    private final UpdateDocumentInfoUseCase updateDocumentInfoUseCase;
    private final GetRandomDocumentUseCase getRandomDocumentUseCase;
    private final FindOneDocumentUseCase findOneDocumentUseCase;
    private final SearchDocumentUseCase searchDocumentUseCase;
    private final DeleteDocumentUseCase deleteDocumentUseCase;
    private final CreateDocumentUseCase createDocumentUseCase;
}
