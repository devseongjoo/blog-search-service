package com.example.blogsearchservice.controller;
import com.example.blogsearchservice.service.KakaoAPIService;
import com.example.blogsearchservice.service.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/kakao")
@RestController()
public class KakaoAPI {

    private String url = "";
    private String key = "b5781416656ae1eac926f04cfc617a75";

    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    KakaoAPIService kakaoAPIService;

    @GetMapping("/")
    public String getKakaoHello() {
        return "Hello Kakao Server";
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseVO> getKakaoBlogSearch(
            @RequestParam(value = "query", required = false, defaultValue = "") String query) {
        if(param.isEmpty()) {
            logger.error("query param empty");
        }
        ResponseVO res = kakaoAPIService.getKakaoBlogInfo(location, query);

        return ResponseEntity.ok(res);
    }
}