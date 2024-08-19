package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentElementDtos;
import com.daemawiki.daemawiki.application.document.usecase.base.DocumentUpdateUseCaseBase;
import reactor.core.publisher.Mono;

public interface UpdateDocumentInfoUseCase extends DocumentUpdateUseCaseBase<DocumentElementDtos.DocumentInfoUpdateDto> {
    Mono<Void> update(String documentId, DocumentElementDtos.DocumentInfoUpdateDto request);
}
