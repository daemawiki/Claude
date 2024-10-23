package com.daemawiki.daemawiki.domain.document;

import com.daemawiki.daemawiki.common.annotation.Constructor;
import com.daemawiki.daemawiki.common.annotation.Entity;
import com.daemawiki.daemawiki.common.annotation.Factory;
import com.daemawiki.daemawiki.common.annotation.ValueObject;
import com.daemawiki.daemawiki.domain.user.model.UserEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@AllArgsConstructor
class DocumentEntity {
    /* fields */

    @Id
    private String id;

    private Title title;

    private List<Detail> detailList;

    private String content;

    private Set<String> categorySet;

    private Long viewCount;

    private Long version;

    private Type type;

    private EditDateTime dateTime;

    private Editor owner;

    private List<Editor> editorList;

    /* static factory methods */
    @Factory
    static DocumentEntity createNewEntity(Title title, List<Detail> detailList, Set<String> categorySet, Type type, Editor owner) {
        return new DocumentEntity(title, detailList, categorySet, type, owner);
    }

    /* constructors */
    @Constructor
    protected DocumentEntity() {
    }

    @Constructor
    private DocumentEntity(Title title, List<Detail> detailList, Set<String> categoryList, Type type, Editor owner) {
        this.title = title;
        this.detailList = detailList;
        this.content = "";
        this.categorySet = categoryList;
        this.viewCount = 0L;
        this.version = 0L;
        this.type = type;
        this.dateTime = EditDateTime.createNewInstance();
        this.owner = owner;
        this.editorList = Collections.emptyList();
    }

    /* value objects */

    @ValueObject
    @AllArgsConstructor(access = AccessLevel.PACKAGE)
    static class EditDateTime {
        private final LocalDateTime createdDateTime;
        @LastModifiedDate
        private LocalDateTime lastModifiedDateTime;

        /**
         * getters <br/><br/>
         * <p>
         * 다른 record value object와 일관성을 유지하기 위해 메서드명 유지
         */
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

    @ValueObject
    record Title(String mainTitle, String subTitle) {
    }

    @ValueObject
    record Detail(String title, String content) {
    }

    @ValueObject
    record Content(String index, String title, String content) {
    }

    @ValueObject
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
     * INCIDENT: 사건사고 문서 :
     */
    @ValueObject
    enum Type {
        STUDENT, MAIN, TEACHER, INCIDENT, TEST
    }
}
