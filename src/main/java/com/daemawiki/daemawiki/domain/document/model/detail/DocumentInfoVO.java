package com.daemawiki.daemawiki.domain.document.model.detail;

import java.util.List;

public record DocumentInfoVO(
        String subTitle,
        List<DocumentInfoDetail> details
) {
    public static DocumentInfoVO of(String subTitle, List<DocumentInfoDetail> details) {
        return new DocumentInfoVO(subTitle, details);
    }
}
