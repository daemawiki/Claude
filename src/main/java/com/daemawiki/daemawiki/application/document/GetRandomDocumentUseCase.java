package com.daemawiki.daemawiki.application.document;

import com.daemawiki.daemawiki.interfaces.document.dto.response.FullDocumentResponse;
import reactor.core.publisher.Mono;

public interface GetRandomDocumentUseCase {
    Mono<FullDocumentResponse> get();
}
