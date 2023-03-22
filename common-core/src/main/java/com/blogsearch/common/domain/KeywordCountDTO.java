package com.blogsearch.common.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KeywordCountDTO {
    String keyword;
    Integer count;

    @Builder
    public KeywordCountDTO(String keyword, Integer count) {
        this.keyword = keyword;
        this.count = count;
    }
}
