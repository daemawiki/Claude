package com.daemawiki.daemawiki.interfaces.document.controller;

import com.daemawiki.daemawiki.application.document.usecase.DocumentEditUseCase;
import com.daemawiki.daemawiki.common.annotation.ui.DocumentApi;
import com.daemawiki.daemawiki.common.util.http.ListRequest;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@DocumentApi
@RequiredArgsConstructor
class DocumentEditController {

    private final DocumentEditUseCase editUseCase;

    @PatchMapping("/{documentId}/editor")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    Mono<Void> editEditors(
            @PathVariable String documentId,
            @RequestBody ListRequest<DocumentElementDtos.Editor> request
    ) {
        return editUseCase.editEditors(documentId, request.list());
    }

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
}
