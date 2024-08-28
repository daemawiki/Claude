package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.common.util.date.EditDateTime;

import java.util.List;

public record SimpleDocumentResult(
        String id,
        String type,
        String title,
        String content,
        List<String> category,
        Long view,
        EditDateTime dateTime
) {
    public static SimpleDocumentResult fromDocumentEntity(DocumentEntity entity) {
        final var contents = entity.getContentList();

        return new SimpleDocumentResult(
                entity.getId(),
                entity.getType(),
                entity.getTitle(),
                contents.isEmpty() ? "none." : contents.getFirst().content(),
                entity.getCategorySet(),
                entity.getViewCount(),
                entity.getDateTime()
        );
    }
}
