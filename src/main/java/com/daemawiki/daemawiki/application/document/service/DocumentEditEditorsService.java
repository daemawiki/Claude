package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.usecase.DocumentEditEditorsUseCase;
import com.daemawiki.daemawiki.domain.document.internal.DocumentEditorInfo;
import com.daemawiki.daemawiki.domain.document.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
class DocumentEditEditorsService extends AbstractDocumentUpdateServiceBase<List<DocumentEditorInfo>> implements DocumentEditEditorsUseCase {

    @Override
    @Transactional
    public Mono<Void> update(String documentId, List<DocumentEditorInfo> updateData) {
        return updateDocument(
                documentId,
                updateData,
                (document, editors) -> document.updateEditors(DocumentElementMapper.toEditorList(editors))
        );
    }

    public DocumentEditEditorsService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}
