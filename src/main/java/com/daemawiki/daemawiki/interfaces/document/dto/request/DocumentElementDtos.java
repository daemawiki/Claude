package com.daemawiki.daemawiki.interfaces.document.dto.request;

import java.util.List;

public class DocumentElementDtos {

    public record DocumentInfoDto(
            String subTitle,
            List<DetailDto> details
    ) {
        public record DetailDto(
                String title,
                String content
        ) {}
    }

    public record DocumentContentDto(
            String index,
            String title,
            String content
    ) {}
}