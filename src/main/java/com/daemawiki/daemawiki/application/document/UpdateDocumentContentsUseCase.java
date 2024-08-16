package com.daemawiki.daemawiki.application.document;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentContent;
import com.daemawiki.daemawiki.application.document.base.DocumentUpdateUseCaseBase;
import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentElementDtos;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UpdateDocumentContentsUseCase extends DocumentUpdateUseCaseBase<List<DocumentElementDtos.DocumentContentDto>> {
    Mono<Void> update(String documentId, List<DocumentElementDtos.DocumentContentDto> updateData);
}
