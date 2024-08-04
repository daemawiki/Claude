package com.daemawiki.daemawiki.domain.document.controller;

import com.daemawiki.daemawiki.domain.document.dto.request.CreateDocumentRequest;
import com.daemawiki.daemawiki.domain.document.dto.response.FullDocumentResponse;
import com.daemawiki.daemawiki.domain.document.dto.request.UpdateDocumentInfoAndCategoryRequest;
import com.daemawiki.daemawiki.domain.document.model.SimpleDocumentResult;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentContent;
import com.daemawiki.daemawiki.domain.document.usecase.*;
import com.daemawiki.daemawiki.global.util.paging.PagingRequest;
import com.daemawiki.daemawiki.global.util.searching.SearchResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

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
            @RequestBody List<DocumentContent> documentContents
    ) {
        return updateDocumentContentsUseCase.update(documentId, documentContents);
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
