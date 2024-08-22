package com.daemawiki.daemawiki.interfaces.document.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class DocumentElementDtos {

    public enum TypeDto {
        STUDENT, MAIN, TEACHER, INCIDENT, TEST
    }

    public record InfoUpdateDto(
            List<DetailDto> detailList,
            TitleDto title
    ) {}

    public record DetailDto(
            String title,
            String content
    ) {}

    public record TitleDto(
            String mainTitle,
            String subTitle
    ) {}

    public record ContentDto(
            String index,
            String title,
            String content
    ) {}

    public record DocumentDto(
            String mainTitle,
            String subTitle,
            Set<String> categoryList
    ) {}

    public record EditorDto(
            String name,
            String userId
    ) {}

    public record EditDateTimeDto(
            LocalDateTime createdDateTime,
            LocalDateTime lastModifiedDateTime
    ) {
        public static EditDateTimeDto createNewInstance() {
            LocalDateTime now = LocalDateTime.now();
            return new EditDateTimeDto(now, now);
        }
    }
}