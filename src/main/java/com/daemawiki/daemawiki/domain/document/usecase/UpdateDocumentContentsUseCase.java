package com.daemawiki.daemawiki.domain.document.usecase;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentContent;
import com.daemawiki.daemawiki.domain.document.usecase.base.DocumentUpdateUseCaseBase;
import reactor.core.publisher.Mono;

import java.util.List;

public interface UpdateDocumentContentsUseCase extends DocumentUpdateUseCaseBase<List<DocumentContent>> {
    Mono<Void> update(String documentId, List<DocumentContent> contents);
}
