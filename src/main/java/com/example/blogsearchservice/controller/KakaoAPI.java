package com.example.blogsearchservice.controller;
import com.example.blogsearchservice.service.KakaoAPIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/kakao")
@RestController()
public class KakaoAPI {

    private String url = "";
    private String key = "b5781416656ae1eac926f04cfc617a75";
    @Autowired
    KakaoAPIService kakaoAPIService;

    @GetMapping("/")
    public String getKakaoHello() {
        return "Hello Kakao Server";
    }

    @GetMapping("/blog/search")
    public Map getKakaoBlogSearch(@RequestParam String query) {
        if(query.equals("")) {
            return new HashMap();
        }
        return kakaoAPIService.getKakaoBlogInfo(query);
    }
}