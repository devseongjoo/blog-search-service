package com.blogsearch.search.service;

import com.blogsearch.common.domain.RequestVO;
import com.blogsearch.common.domain.ResponseVO;
import com.blogsearch.common.domain.DocumentVO;
import com.blogsearch.search.util.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KakaoService {

//    @Autowired
//    StatsService statsService;

    @Autowired
    RestTemplateService restTemplateService;

    public List<DocumentVO> getBlogInfo(RequestVO requestVO) {
        List<DocumentVO> documents = new ArrayList<DocumentVO>();
        try {
            ResponseEntity<ResponseVO> response = restTemplateService.fetchKakaoOpenAPI(requestVO);
            if(response.getStatusCode()
            documents = responseVO.getDocuments();

            String keyword = Tokenizer.getKeywordToken(requestVO.getQuery());

//            statsService.increaseCount(keyword);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return documents;
    }
}
