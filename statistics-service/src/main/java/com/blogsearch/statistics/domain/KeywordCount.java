package com.blogsearch.statistics.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Table(name = "KEYWORD_COUNT")
@Entity
public class KeywordCount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "COUNT")
    private Integer count;

    public KeywordCount(String keyword, Integer count) {
        this.keyword = keyword;
        this.count = count;
    }

    public KeywordCount() {

    }
}
