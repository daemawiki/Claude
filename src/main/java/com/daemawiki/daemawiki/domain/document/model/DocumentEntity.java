package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.document.dto.request.UpdateDocumentInfoAndCategoryRequest;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentContent;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentEditor;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentInfo;
import com.daemawiki.daemawiki.domain.document.model.detail.DocumentType;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import com.daemawiki.daemawiki.domain.user.model.detail.UserRole;
import com.daemawiki.daemawiki.global.utils.date.EditDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
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

    private DocumentEditor owner;

    private List<DocumentEditor> editors;

    public boolean canEdit(UserEntity user) {
        var editor = DocumentEditor.fromUser(user);
        return (editors.contains(editor) || isOwner(editor))
                && (!UserRole.DSM_MOP.equals(user.getRole()));
    }

    public boolean isOwner(DocumentEditor editor) {
        return owner.equals(editor);
    }

    public void updateDocumentInfoAndCategory(UpdateDocumentInfoAndCategoryRequest infoAndCatefory) {
        this.info = infoAndCatefory.info();
        this.category = infoAndCatefory.category();
    }

    public void updateEditors(List<DocumentEditor> editors) {
        this.editors = editors;
    }

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

    public static DocumentEntity createEntity(String title, DocumentInfo info, List<String> category, DocumentType type, DocumentEditor owner) {
        return new DocumentEntity(title, info, category, type, owner);
    }

    protected DocumentEntity() {}

    private DocumentEntity(String title, DocumentInfo info, List<String> category, DocumentType type, DocumentEditor owner) {
        this.title = title;
        this.info = info;
        this.contents = Collections.emptyList();
        this.category = category;
        this.view = 0L;
        this.version = 0L;
        this.type = type;
        this.dateTime = EditDateTime.getNowInstance();
        this.owner = owner;
        this.editors = Collections.emptyList();
    }
}
