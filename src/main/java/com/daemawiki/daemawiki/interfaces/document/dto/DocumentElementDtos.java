package com.daemawiki.daemawiki.interfaces.document.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * Layer 간의 document element를 정의하고, 전달할 수 있는 클래스 모임입니다.
 */
public class DocumentElementDtos {

    public enum Type {
        STUDENT, MAIN, TEACHER, INCIDENT, TEST
    }

    public record UpdateInfo(
            List<Detail> detailList,
            Title title
    ) {}

    public record Detail(
            String title,
            String content
    ) {}

    public record Title(
            String mainTitle,
            String subTitle
    ) {}

    public record Content(
            String index,
            String title,
            String content
    ) {}

    public record Document(
            String mainTitle,
            String subTitle,
            Set<String> categoryList
    ) {}

    public record Editor(
            String name,
            String userId
    ) {}

    public record EditDateTime(
            LocalDateTime createdDateTime,
            LocalDateTime lastModifiedDateTime
    ) {
        public static EditDateTime createNewInstance() {
            LocalDateTime now = LocalDateTime.now();
            return new EditDateTime(now, now);
        }
    }
}