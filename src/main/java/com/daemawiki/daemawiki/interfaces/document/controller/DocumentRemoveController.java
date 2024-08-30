package com.daemawiki.daemawiki.interfaces.document.controller;

import com.daemawiki.daemawiki.application.document.usecase.DocumentRemoveUseCase;
import com.daemawiki.daemawiki.common.annotation.ui.DocumentApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

@DocumentApi
@RequiredArgsConstructor
class DocumentRemoveController {
    private final DocumentRemoveUseCase removeUseCase;

    @DeleteMapping("/{documentId}")
    Mono<Void> remove(
            @PathVariable String documentId
    ) {
        return removeUseCase.remove(documentId);
    }
}
