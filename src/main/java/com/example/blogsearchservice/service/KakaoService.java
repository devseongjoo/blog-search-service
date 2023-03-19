package com.example.blogsearchservice.service;

import com.example.blogsearchservice.service.VO.DocumentVO;
import com.example.blogsearchservice.service.VO.RequestVO;
import com.example.blogsearchservice.service.VO.ResponseVO;
import com.example.blogsearchservice.util.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KakaoService {

    @Autowired
    StatsService statsService;

    @Autowired
    RestTemplateService restTemplateService;

    public List<DocumentVO> getBlogInfo(RequestVO requestVO) {
        List<DocumentVO> documents = new ArrayList<DocumentVO>();
        try {
            ResponseVO responseVO = restTemplateService.fetchOpenAPI(requestVO);
            documents = responseVO.getDocuments();

            String keyword = Tokenizer.getKeywordToken(requestVO.getQuery());

            statsService.increaseCount(keyword);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
}
