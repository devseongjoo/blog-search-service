package com.example.blogsearchservice.service;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class RestTemplateService {

    @Value("${spring.kakao.common.url}")
    private String kakaoUrl;

    @Value("${spring.kakao.location.search.blog}")
    private String locationSearchBlog;

    @Value("${spring.kakao.location.search.web}")
    private String locationSearchWeb;

    @Value("${spring.kakao.common.rest_api_key}")
    private String kakaoRestAPIKey;

    //Todo : To Logger Interceptor for RestTemplate
    private Logger logger = LoggerFactory.getLogger(getClass());

    //Todo: Kakao 이외의 검색 소스가 포함될 수 있도록
    public ResponseEntity<Map> fetchOpenAPI(String type, String keyword) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        factory.setReadTimeout(5000); // read timeout
        factory.setConnectTimeout(3000); // connection timeout

        //Apache HttpComponents HttpClient
        HttpClient httpClient = HttpClientBuilder.create()
                .setMaxConnTotal(50)//최대 커넥션 수
                .setMaxConnPerRoute(20).build();
        //각 호스트(IP와 Port의 조합)당 커넥션 풀에 생성가능한 커넥션 수
        factory.setHttpClient(httpClient);

        RestTemplate restTemplate = new RestTemplate(factory);

        String location = null;
        String query = null;
        if(type.equals("blog")) {
            location = locationSearchBlog;
            query = "이효리";
        } else if(type.equals("web")) {
            location = locationSearchWeb;
            query = "https://brunch.co.kr/@tourism 집짓기";
        }

        //<-- Build URI -->
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(kakaoUrl)
                .path(location)
                .queryParam("query", query)
                .build();

        //<-- Http Headers -->
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK "+ kakaoRestAPIKey);

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("query", queryWeb);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //<-- Check encoded before send-->
        logger.info(builder.toString());
        logger.info(builder.toUri().toString());
        logger.info(builder.toUriString());

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class
        );

        return responseEntity;
    }
}
