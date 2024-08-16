package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.document.usecase.UpdateDocumentContentsUseCase;
import com.daemawiki.daemawiki.domain.document.model.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.interfaces.document.dto.request.DocumentElementDtos;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
class UpdateDocumentContentsService extends AbstractDocumentUpdateServiceBase<List<DocumentElementDtos.DocumentContentDto>> implements UpdateDocumentContentsUseCase {

    @Override
    public Mono<Void> update(String documentId, List<DocumentElementDtos.DocumentContentDto> updateData) {
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
