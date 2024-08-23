package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.document.usecase.UpdateDocumentInfoUseCase;
import com.daemawiki.daemawiki.domain.document.DocumentElementMapper;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Service
class UpdateDocumentInfoInfoService extends AbstractDocumentUpdateServiceBase<DocumentElementDtos.InfoUpdateDto> implements UpdateDocumentInfoUseCase {

    @Override
    @Transactional
    public Mono<Void> update(String documentId, DocumentElementDtos.InfoUpdateDto request) {
        return updateDocument(
                documentId,
                request,
                (document, dto) -> document.updateDocumentInfo(DocumentElementMapper.toInfoTuple(dto))
        );
    }

    public UpdateDocumentInfoInfoService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}