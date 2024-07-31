package com.daemawiki.daemawiki.domain.document.service;

import com.daemawiki.daemawiki.domain.document.dto.request.UpdateDocumentInfoAndCategoryRequest;
import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.repository.DocumentRepository;
import com.daemawiki.daemawiki.domain.document.service.base.AbstractDocumentUpdateServiceBase;
import com.daemawiki.daemawiki.domain.document.usecase.UpdateDocumentInfoUseCase;
import com.daemawiki.daemawiki.domain.user.component.CurrentUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UpdateDocumentInfoService extends AbstractDocumentUpdateServiceBase<UpdateDocumentInfoAndCategoryRequest> implements UpdateDocumentInfoUseCase {

    @Override
    public Mono<Void> update(String documentId, UpdateDocumentInfoAndCategoryRequest request) {
        return updateDocument(documentId, request, DocumentEntity::updateDocumentInfoAndCategory);
    }

    public UpdateDocumentInfoService(DocumentRepository documentRepository, CurrentUser currentUser) {
        super(documentRepository, currentUser);
    }
}