package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.application.document.usecase.base.DocumentUpdateUseCaseBase;
import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentElementDtos;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UpdateDocumentContentsUseCase extends DocumentUpdateUseCaseBase<List<DocumentElementDtos.DocumentContentDto>> {
    Mono<Void> update(String documentId, List<DocumentElementDtos.DocumentContentDto> updateData);
}
