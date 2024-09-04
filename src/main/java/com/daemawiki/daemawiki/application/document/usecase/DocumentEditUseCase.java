package com.daemawiki.daemawiki.application.document.usecase;

import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DocumentEditUseCase {
    Mono<Void> editInfo(String documentId, DocumentElementDtos.UpdateInfo request);

    Mono<Void> editEditors(String documentId, List<DocumentElementDtos.Editor> updateData);

    Mono<Void> editContents(String documentId, List<DocumentElementDtos.Content> updateData);
}
