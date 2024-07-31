package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.dto.request.UpdateDocumentInfoAndCategoryRequest;
import com.daemawiki.daemawiki.domain.document.usecase.base.DocumentUpdateUseCaseBase;
import reactor.core.publisher.Mono;

public interface UpdateDocumentInfoUseCase extends DocumentUpdateUseCaseBase<UpdateDocumentInfoAndCategoryRequest> {
    Mono<Void> update(String documentId, UpdateDocumentInfoAndCategoryRequest request);
}
