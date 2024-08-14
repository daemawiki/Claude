package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.common.util.date.EditDateTime;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Document(collection = "documents")
@Getter(value = AccessLevel.PUBLIC)
public class DocumentEntity {
    @Id
    private String id;

    private String title;

    private Info info;

    private List<Content> contentList;

    private Set<String> categoryList;

    private Long view;

    private Long version;

    private Type type;

    private EditDateTime dateTime;

    private Editor owner;

    private List<Editor> editorList;

    /* setter And more. */

    public boolean canEdit(Editor editor) { // TODO: 8/13/24 DSM_MOP 검사 로직 옮기기
        return editorList.contains(editor) || isOwner(editor);
    }

    public boolean isOwner(Editor editor) {
        return owner.equals(editor);
    }

    public void updateDocumentInfoAndCategory(Info info, Set<String> categoryList) {
        this.info = info;
        this.categoryList = categoryList;
    }

    public void updateEditors(List<Editor> editorList) {
        this.editorList = editorList;
    }

    public void updateContents(List<Content> contentList) {
        this.contentList = contentList;
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

    /* static factory methods */

    public static DocumentEntity createEntity(String title, Info info, Set<String> categoryList, Type type, Editor owner) {
        return new DocumentEntity(title, info, categoryList, type, owner);
    }

    /* constructors */

    protected DocumentEntity() {}

    private DocumentEntity(String title, Info info, Set<String> categoryList, Type type, Editor owner) {
        this.title = title;
        this.info = info;
        this.contentList = Collections.emptyList();
        this.categoryList = categoryList;
        this.view = 0L;
        this.version = 0L;
        this.type = type;
        this.dateTime = EditDateTime.getNewInstance();
        this.owner = owner;
        this.editorList = Collections.emptyList();
    }

    /* value objects */

    public record Info(String subTitle, List<Detail> detailList) {

        public record Detail(String title, String content) {}
    }

    public record Content(String index, String title, String content) {}

    public record Editor(String name, String userId) {
        public static Editor fromUser(UserEntity user) {
            return new Editor(user.getName(), user.getId());
        }
    }

    public enum Type {
        STUDENT,
        MAIN,
        TEACHER,
        INCIDENT,
        TEST
    }
}
