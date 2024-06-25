package com.daemawiki.daemawiki.domain.user.model.detail;

public record ClassInfo(
        Integer year,
        Integer grade,
        Integer classNumber,
        Integer number
) {
    public static ClassInfo of(Integer year, Integer grade, Integer classNumber, Integer number) {
        return new ClassInfo(year, grade, classNumber, number);
    }
}
