package com.example.blogsearchservice.service;

import lombok.Data;

import java.io.Serializable;

@Data
public class DocumentVO implements Serializable {
    //블로그 글 제목
    String	title;
    //블로그 글 요약
    String	contents;

    //블로그 글 URL
    String url;

    //블로그의 이름
    String blogname;

    //검색 시스템에서 추출한 대표 미리보기 이미지 URL, 미리보기 크기 및 화질은 변경될 수 있음
    String thumbnail;

    //블로그 글 작성시간, ISO 8601 [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
    String datetime;
}
