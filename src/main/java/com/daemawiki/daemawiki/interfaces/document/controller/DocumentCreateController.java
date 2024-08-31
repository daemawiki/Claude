package com.daemawiki.daemawiki.interfaces.document.controller;

import com.daemawiki.daemawiki.application.document.usecase.DocumentCreateUseCase;
import com.daemawiki.daemawiki.common.annotation.ui.DocumentApi;
import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@DocumentApi
@RequiredArgsConstructor
class DocumentCreateController {
    private final DocumentCreateUseCase createUseCase;

    @PostMapping
    Mono<Void> create(
            @RequestBody DocumentCreateRequest request
    ) {
        return createUseCase.create(request);
    }
}
