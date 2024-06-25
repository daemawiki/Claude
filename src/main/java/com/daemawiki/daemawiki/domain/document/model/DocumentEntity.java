package com.daemawiki.daemawiki.domain.document.model;

import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
@Getter(value = AccessLevel.PUBLIC)
public class DocumentEntity {
    @Id
    private String id;

    private String title;

    public static DocumentEntity createEntity(String title) {
        return new DocumentEntity(title);
    }

    protected DocumentEntity() {}

    private DocumentEntity(String title) {
        this.title = title;
    }
}
