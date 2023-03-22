package com.blogsearch.common.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ResponseVO implements Serializable {

    HttpStatus statusCode;

    MetaVO meta;

    List<DocumentVO> documents;
}
