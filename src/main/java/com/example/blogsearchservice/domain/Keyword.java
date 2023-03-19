package com.example.blogsearchservice.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class Keyword implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "KEYWORD")
    private String keyword;

    @Column(name = "COUNT")
    private Integer count;

    public Keyword(String keyword, Integer count) {
        this.keyword = keyword;
        this.count = count;
    }
}
