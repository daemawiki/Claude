package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.UpdateDocumentEditorsUseCase;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
class UpdateDocumentEditorsService extends AbstractDocumentUpdateServiceBase<List<DocumentEditor>> implements UpdateDocumentEditorsUseCase {

    @Override
    public Mono<Void> update(String documentId, List<DocumentEditor> updateData) {
        return updateDocument(documentId, updateData, DocumentEntity::updateEditors);
    }

    public UpdateDocumentEditorsService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}
