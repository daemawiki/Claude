package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.global.utils.EditDateTime;
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

    private DocumentInfo info;

    private Long view;

    private Long version;

    private DocumentType type;

    private EditDateTime dateTime;

    public void updateTitle(String title) {
        this.title = title;
    }

    public void increaseView() {
        this.view++;
    }

    public void increaseVersion() {
        this.version++;
    }

    public static DocumentEntity createEntity(String title, DocumentInfo info, DocumentType type) {
        return new DocumentEntity(title, info, type);
    }

    protected DocumentEntity() {}

    private DocumentEntity(String title, DocumentInfo info, DocumentType type) {
        this.title = title;
        this.info = info;
        this.view = 0L;
        this.version = 0L;
        this.type = type;
        this.dateTime = EditDateTime.getNowInstance();
    }
}
