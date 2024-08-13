package com.daemawiki.daemawiki.application.document;

import com.daemawiki.daemawiki.interfaces.document.dto.request.UpdateDocumentInfoAndCategoryRequest;
import com.daemawiki.daemawiki.application.document.base.DocumentUpdateUseCaseBase;
import reactor.core.publisher.Mono;

public interface UpdateDocumentInfoUseCase extends DocumentUpdateUseCaseBase<UpdateDocumentInfoAndCategoryRequest> {
    Mono<Void> update(String documentId, UpdateDocumentInfoAndCategoryRequest request);
}
