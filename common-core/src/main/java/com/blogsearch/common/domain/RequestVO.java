package com.blogsearch.common.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RequestVO {
    @NotNull
    String query;

    String sort;

    Integer page;

    Integer size;
}
