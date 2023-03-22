package com.blogsearch.search.controller;
import com.blogsearch.common.domain.KeywordCountDTO;
import com.blogsearch.common.domain.RequestVO;
import com.blogsearch.common.domain.DocumentVO;
import com.blogsearch.search.service.BlogSearchService;


import com.blogsearch.search.service.RestTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/search")
@RestController()
public class KakaoAPIController {

    private String url = "";
    private String key = "b5781416656ae1eac926f04cfc617a75";

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    BlogSearchService blogSearchService;

    @Autowired
    RestTemplateService restTemplateService;

    @GetMapping("/blog/paged")
    public List<DocumentVO> getBlogSearchPagedList(final @Valid HttpServletRequest req) {

        RequestVO requestVO = new RequestVO();
        requestVO.setQuery(req.getParameter("query"));
        requestVO.setSort(req.getParameter("sort"));
        requestVO.setPage(Integer.parseInt(req.getParameter("page")));
        requestVO.setSize(Integer.parseInt(req.getParameter("size")));

        try {
            //<-- get blog info -->
            List<DocumentVO> res = blogSearchService.getBlogInfo(requestVO);
            return res;
        } catch(Exception e) {
            //<-- exception handling -->
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/statistics/keywords")
    public List<KeywordCountDTO> readTopRatedList() {
        ResponseEntity<List<KeywordCountDTO>> response = restTemplateService.getTopRatedList();

        if(response.getStatusCode() == HttpStatus.OK) {
            List<KeywordCountDTO> list = response.getBody();
            if(list.isEmpty()) {
                return new ArrayList<>();
            }
            return list;
        }
        return null;
    }
}