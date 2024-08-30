package com.daemawiki.daemawiki.application.document.service;

import com.daemawiki.daemawiki.application.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.application.document.usecase.DocumentEditInfoUseCase;
import com.daemawiki.daemawiki.domain.document.DocumentRepository;
import com.daemawiki.daemawiki.application.user.component.CurrentUser;
import com.daemawiki.daemawiki.interfaces.document.dto.DocumentElementDtos;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
class DocumentEditInfoService extends AbstractDocumentUpdateServiceBase<DocumentElementDtos.UpdateInfo> implements DocumentEditInfoUseCase {

    @Override
    @Transactional
    public Mono<Void> update(String documentId, DocumentElementDtos.UpdateInfo request) {
        return updateDocument(
                documentId,
                request,
                (document, dto) -> document.updateDocumentInfo(
                        Tuples.of(dto.detailList(), dto.title())
                )
        );
    }

    public DocumentEditInfoService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}