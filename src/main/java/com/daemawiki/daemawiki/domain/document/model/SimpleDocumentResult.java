package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.global.util.date.EditDateTime;

import java.util.List;

public record SimpleDocumentResult(
        String id,
        DocumentType type,
        String title,
        String content,
        List<String> category,
        Long view,
        EditDateTime dateTime
) {
    public static SimpleDocumentResult fromDocumentEntity(DocumentEntity entity) {
        final var contents = entity.getContents();

        return new SimpleDocumentResult(
                entity.getId(),
                entity.getType(),
                entity.getTitle(),
                contents.isEmpty() ? "none." : contents.getFirst().content(),
                entity.getCategory(),
                entity.getView(),
                entity.getDateTime()
        );
    }
}
