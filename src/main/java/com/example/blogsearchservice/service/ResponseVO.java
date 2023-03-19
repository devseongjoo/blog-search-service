package com.example.blogsearchservice.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class ResponseVO implements Serializable {

    MetaVO meta;

    List<Map<String, Object>> documents;
}
