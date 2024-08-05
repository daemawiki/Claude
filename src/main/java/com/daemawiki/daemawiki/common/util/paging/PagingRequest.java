package com.daemawiki.daemawiki.common.util.paging;

import com.daemawiki.daemawiki.common.util.sorting.SortByOption;
import com.daemawiki.daemawiki.common.util.sorting.SortDirection;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record PagingRequest(
        @Nullable
        @Pattern(regexp = "^(CREATED|UPDATED|TITLE|VIEW)$")
        String sort,
        @Nullable
        @Pattern(regexp = "^(ASC|DESC)$")
        String direction,
        @Nullable
        @Min(0)
        Integer page,
        @Nullable
        @Min(1) @Max(30)
        Integer size
) {
    public PagingRequest {
        sort = (sort == null) ? SortByOption.CREATED.name() : sort;
        direction = (direction == null) ? SortDirection.DESC.name() : direction;
        page = (page == null) ? 0 : page;
        size = (size == null) ? 10 : size;
    }
}
