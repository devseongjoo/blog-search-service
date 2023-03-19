package com.example.blogsearchservice.service;

import com.example.blogsearchservice.service.VO.RequestVO;
import com.example.blogsearchservice.service.VO.ResponseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public ResponseVO fetchOpenAPI(RequestVO requestVO) throws Exception {
        ResponseVO responseVO = new ResponseVO();
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

        String location = locationSearchWeb;
        String query = "이효리";

        //<-- Build URI -->
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(kakaoUrl)
                .path(location)
                .queryParam("query", requestVO.getQuery())
                .queryParam("sort", requestVO.getSort())
                .queryParam("page", requestVO.getPage())
                .queryParam("size", requestVO.getSize())
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

        ResponseEntity<String> response = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

//        throws JsonProcessingException/
        try {
            if(response.getStatusCode().value() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                responseVO = objectMapper.readValue(response.getBody(), ResponseVO.class);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseVO();
        }
        return responseVO;
    }
}
