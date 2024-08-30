package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.interfaces.document.dto.request.CreateDocumentRequest;
import reactor.core.publisher.Mono;

public interface DocumentCreateUseCase {
    Mono<Void> create(CreateDocumentRequest request);
}
