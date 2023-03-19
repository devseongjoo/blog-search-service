package com.example.blogsearchservice.service;

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
