package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.document.model.detail.DocumentContent;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.global.utils.EditDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "documents")
@Getter(value = AccessLevel.PUBLIC)
public class DocumentEntity {
    @Id
    private String id;

    private String title;

    private DocumentInfo info;

    private List<DocumentContent> contents;

    private List<String> category;

    private Long view;

    private Long version;

    private DocumentType type;

    private EditDateTime dateTime;

    private List<DocumentEditor> editors;

    public void updateContents(List<DocumentContent> contents) {
        this.contents = contents;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void increaseView() {
        this.view++;
    }

    public void increaseVersion() {
        this.version++;
    }

    public static DocumentEntity createEntity(String title, DocumentInfo info, List<String> category, DocumentType type) {
        return new DocumentEntity(title, info, category, type);
    }

    protected DocumentEntity() {}

    private DocumentEntity(String title, DocumentInfo info, List<String> category, DocumentType type) {
        this.title = title;
        this.info = info;
        this.contents = null;
        this.category = category;
        this.view = 0L;
        this.version = 0L;
        this.type = type;
        this.dateTime = EditDateTime.getNowInstance();
    }
}
