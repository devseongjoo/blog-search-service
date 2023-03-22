package com.blogsearch.search.service;

import com.blogsearch.common.domain.RequestVO;
import com.blogsearch.common.domain.ResponseVO;
import com.blogsearch.common.domain.DocumentVO;
import com.blogsearch.search.util.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;

@Service
public class BlogSearchService {

    @Autowired
    RestTemplateService restTemplateService;

    public List<DocumentVO> getBlogInfo(RequestVO requestVO) {
        List<DocumentVO> documents = new ArrayList<DocumentVO>();
        try {
            ResponseEntity<ResponseVO> response = restTemplateService.fetchKakaoOpenAPI(requestVO);
            documents = response.getBody().getDocuments();

            if(response.getStatusCode() == HttpStatus.OK) {
                String keyword = Tokenizer.getKeywordToken(requestVO.getQuery());
                restTemplateService.updateKeyword(keyword);
            }

        } catch (HttpServerErrorException e) {
            //Todo: <-- TEST with Mock needed -->
            String keyword = Tokenizer.getKeywordToken(requestVO.getQuery());
            restTemplateService.fetchNaverOpenAPI(keyword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documents;
    }
}
