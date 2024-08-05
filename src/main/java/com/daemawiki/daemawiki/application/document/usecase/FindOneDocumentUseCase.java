package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import reactor.core.publisher.Mono;

public interface FindOneDocumentUseCase {
    Mono<FullDocumentResponse> findById(String documentId);
}
