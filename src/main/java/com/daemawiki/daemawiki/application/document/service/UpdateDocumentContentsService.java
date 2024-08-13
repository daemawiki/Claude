package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.document.UpdateDocumentContentsUseCase;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentContent;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
class UpdateDocumentContentsService extends AbstractDocumentUpdateServiceBase<List<DocumentContent>> implements UpdateDocumentContentsUseCase {

    @Override
    public Mono<Void> update(String documentId, List<DocumentContent> contents) {
        return updateDocument(documentId, contents, DocumentEntity::updateContents);
    }

    public UpdateDocumentContentsService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}
