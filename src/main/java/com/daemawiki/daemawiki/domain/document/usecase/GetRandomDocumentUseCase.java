package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.dto.RandomDocumentResponse;
import reactor.core.publisher.Mono;

public interface GetRandomDocumentUseCase {
    Mono<RandomDocumentResponse> get();
}
