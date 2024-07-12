package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.dto.request.CreateDocumentRequest;
import reactor.core.publisher.Mono;

public interface CreateDocumentUseCase {
    Mono<Void> create(CreateDocumentRequest request);
}
