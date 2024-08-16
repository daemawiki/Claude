package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.UpdateDocumentEditorsUseCase;
import com.daemawiki.daemawiki.domain.document.internal.DocumentEditorInfo;
import com.daemawiki.daemawiki.domain.document.model.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
class UpdateDocumentEditorsService extends AbstractDocumentUpdateServiceBase<List<DocumentEditorInfo>> implements UpdateDocumentEditorsUseCase {

    @Override
    public Mono<Void> update(String documentId, List<DocumentEditorInfo> updateData) {
        return updateDocument(
                documentId,
                updateData,
                (document, editors) -> document.updateEditors(DocumentElementMapper.toEditorList(editors))
        );
    }

    public UpdateDocumentEditorsService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}
