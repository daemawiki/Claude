package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.document.usecase.UpdateDocumentContentsUseCase;
import com.daemawiki.daemawiki.domain.document.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
class UpdateDocumentContentsService extends AbstractDocumentUpdateServiceBase<List<DocumentElementDtos.ContentDto>> implements UpdateDocumentContentsUseCase {

    @Override
    @Transactional
    public Mono<Void> update(String documentId, List<DocumentElementDtos.ContentDto> updateData) {
        return updateDocument(
                documentId,
                updateData,
                (document, contents) -> document.updateContents(DocumentElementMapper.toContentList(contents))
        );
    }

    public UpdateDocumentContentsService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}
