package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfoVO;
import reactor.core.publisher.Mono;

public interface UpdateDocumentInfoUseCase {
    Mono<Void> update(String documentId, DocumentInfoVO request);
}
