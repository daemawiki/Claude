package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.dto.FullDocumentResponse;
import reactor.core.publisher.Mono;

public interface GetRandomDocumentUseCase {
    Mono<FullDocumentResponse> get();
}
