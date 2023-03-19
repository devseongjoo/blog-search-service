package com.example.blogsearchservice.service.VO;

import com.example.blogsearchservice.service.VO.DocumentVO;
import com.example.blogsearchservice.service.VO.MetaVO;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseVO implements Serializable {

    HttpStatus statusCode;

    MetaVO meta;

    List<DocumentVO> documents;
}
