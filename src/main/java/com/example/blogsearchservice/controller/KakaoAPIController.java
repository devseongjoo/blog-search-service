package com.example.blogsearchservice.controller;
import com.example.blogsearchservice.service.VO.DocumentVO;
import com.example.blogsearchservice.service.KakaoService;
import com.example.blogsearchservice.service.VO.RequestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/kakao")
@RestController()
public class KakaoAPIController {

    private String url = "";
    private String key = "b5781416656ae1eac926f04cfc617a75";

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    KakaoService kakaoService;

    @GetMapping("/readPagedList")
    public List<DocumentVO> getKakaoBlogSearchPagedList(final @Valid HttpServletRequest req) {

        RequestVO requestVO = new RequestVO();
        requestVO.setQuery(req.getParameter("query"));
        requestVO.setSort(req.getParameter("sort"));
        requestVO.setPage(Integer.parseInt(req.getParameter("page")));
        requestVO.setSize(Integer.parseInt(req.getParameter("size")));

        List<DocumentVO> res = kakaoService.getBlogInfo(requestVO);

        return res;
    }
}