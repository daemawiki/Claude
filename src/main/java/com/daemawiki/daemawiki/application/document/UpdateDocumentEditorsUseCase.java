package com.daemawiki.daemawiki.application.document;

import com.daemawiki.daemawiki.domain.document.internal.DocumentEditorInfo;
import com.daemawiki.daemawiki.application.document.base.DocumentUpdateUseCaseBase;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UpdateDocumentEditorsUseCase extends DocumentUpdateUseCaseBase<List<DocumentEditorInfo>> {
    Mono<Void> update(String documentId, List<DocumentEditorInfo> updateData);
}
