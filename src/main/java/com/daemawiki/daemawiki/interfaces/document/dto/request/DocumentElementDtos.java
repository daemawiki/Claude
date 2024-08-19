package com.daemawiki.daemawiki.interfaces.document.dto.request;

import java.util.List;
import java.util.Set;

public class DocumentElementDtos {
    public record DocumentInfoUpdateDto(
            List<DocumentDetailDto> detailList,
            DocumentTitleDto title
    ) {
        public record DocumentDetailDto(
                String title,
                String content
        ) {}

        public record DocumentTitleDto(
                String mainTitle,
                String subTitle
        ) {}
    }

    public record DocumentContentDto(
            String index,
            String title,
            String content
    ) {}

    public record DocumentDto(
            String mainTitle,
            String subTitle,
            Set<String> categoryList
    ) {}
}