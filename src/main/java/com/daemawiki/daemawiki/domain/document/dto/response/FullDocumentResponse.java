package com.daemawiki.daemawiki.domain.document.dto.response;

import com.daemawiki.daemawiki.domain.document.model.DocumentEntity;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentContent;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.global.utils.date.EditDateTime;

import java.util.List;

public record FullDocumentResponse(
        String id,
        String title,
        DocumentInfo info,
        List<DocumentContent> contents,
        List<String> category,
        Long view,
        Long version,
        DocumentType type,
        EditDateTime dateTime
) {
    public static FullDocumentResponse fromDocumentEntity(DocumentEntity entity) {
        return new FullDocumentResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getInfo(),
                entity.getContents(),
                entity.getCategory(),
                entity.getView(),
                entity.getVersion(),
                entity.getType(),
                entity.getDateTime()
        );
    }
}
