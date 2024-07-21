package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.dto.response.FullDocumentResponse;
import reactor.core.publisher.Mono;

public interface FindOneDocumentUseCase {
    Mono<FullDocumentResponse> findById(String documentId);
}
