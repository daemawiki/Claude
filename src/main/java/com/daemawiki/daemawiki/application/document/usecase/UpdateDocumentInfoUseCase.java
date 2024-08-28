package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import com.daemawiki.daemawiki.application.document.usecase.base.DocumentUpdateUseCaseBase;
import reactor.core.publisher.Mono;

public interface UpdateDocumentInfoUseCase extends DocumentUpdateUseCaseBase<DocumentElementDtos.UpdateInfo> {
    Mono<Void> update(String documentId, DocumentElementDtos.UpdateInfo request);
}
