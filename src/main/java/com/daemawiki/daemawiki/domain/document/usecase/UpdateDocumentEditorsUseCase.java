package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;
import com.daemawiki.daemawiki.domain.document.usecase.base.DocumentUpdateUseCaseBase;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UpdateDocumentEditorsUseCase extends DocumentUpdateUseCaseBase<List<DocumentEditor>> {
    Mono<Void> update(String documentId, List<DocumentEditor> updateData);
}
