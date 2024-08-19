package com.daemawiki.daemawiki.domain.document.model;

import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import reactor.util.function.Tuple2;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Document(collection = "documents")
@Getter(value = AccessLevel.PUBLIC)
public class DocumentEntity {
    /* fields */

    @Id
    private String id;

    private Title title;

    private List<Detail> detailList;

    private List<Content> contentList;

    private Set<String> categoryList;

    private Long view;

    private Long version;

    private Type type;

    private EditDateTime dateTime;

    private Editor owner;

    private List<Editor> editorList;

    /* validate methods */

    public boolean canEdit(Editor editor) { // TODO: 8/13/24 DSM_MOP 검사 로직 옮기기
        return isOwner(editor) || editorList.contains(editor);
    }

    public boolean canDelete(Editor editor) {
        return canEdit(editor) && !type.equals(Type.STUDENT);
    }

    public boolean isOwner(Editor editor) {
        return owner.equals(editor);
    }

    /* setters */

    public void updateEditors(List<Editor> editorList) {
        this.editorList = editorList;
        increaseVersion();
    }

    public void updateContents(List<Content> contentList) {
        this.contentList = contentList;
        increaseVersion();
    }

    public void updateDocumentInfo(Tuple2<List<Detail>, Title> tuple) {
        updateDetailList(tuple.getT1());
        updateTitle(tuple.getT2());
    }

    private void updateTitle(Title title) {
        this.title = title;
    }

    private void updateDetailList(List<Detail> detailList) {
        this.detailList = detailList;
    }

    public void increaseView() {
        view++;
    }

    private void increaseVersion() {
        version++;
    }

    /* static factory methods */

    public static DocumentEntity createEntity(Title title, List<Detail> detailList, Set<String> categoryList, Type type, Editor owner) {
        return new DocumentEntity(title, detailList, categoryList, type, owner);
    }

    /* constructors */

    protected DocumentEntity() {}

    private DocumentEntity(Title title, List<Detail> detailList, Set<String> categoryList, Type type, Editor owner) {
        this.title = title;
        this.detailList = detailList;
        this.contentList = Collections.emptyList();
        this.categoryList = categoryList;
        this.view = 0L;
        this.version = 0L;
        this.type = type;
        this.dateTime = EditDateTime.createNewInstance();
        this.owner = owner;
        this.editorList = Collections.emptyList();
    }

    /* value objects */

    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    static class EditDateTime {
        private final LocalDateTime createdDateTime;
        @LastModifiedDate
        private LocalDateTime lastModifiedDateTime;

        /**
         * getters <br/><br/>
         *
         * 다른 record value object와 일관성을 유지하기 위해 메서드명 유지
         * */
        public LocalDateTime createdDateTime() {
            return createdDateTime;
        }

        public LocalDateTime lastModifiedDateTime() {
            return lastModifiedDateTime;
        }

        public static EditDateTime createNewInstance() {
            final var now = LocalDateTime.now();
            return new EditDateTime(now, now);
        }
    }

    record Title(String mainTitle, String subTitle) {}

    record Detail(String title, String content) {}

    record Content(String index, String title, String content) {}

    record Editor(String name, String userId) {
        public static Editor fromUser(UserEntity user) {
            return new Editor(user.getName(), user.getId());
        }
    }

    /**
     * <pre>
     * STUDENT : 학생 문서    : 삭제 불가능
     * MAIN    : 메인 페이지   : 삭제 불가능
     * TEACHER : 선생님 문서   :
     * INCIDENT: 사건사고 문서 :*/
    enum Type {
        STUDENT, MAIN, TEACHER, INCIDENT, TEST
    }
}
