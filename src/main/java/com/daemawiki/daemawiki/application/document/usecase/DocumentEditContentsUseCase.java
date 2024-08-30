package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.application.document.usecase.base.DocumentUpdateUseCaseBase;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DocumentEditContentsUseCase extends DocumentUpdateUseCaseBase<List<DocumentElementDtos.Content>> {
    Mono<Void> update(String documentId, List<DocumentElementDtos.Content> updateData);
}
