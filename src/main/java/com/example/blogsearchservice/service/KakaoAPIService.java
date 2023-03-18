package com.example.blogsearchservice.service;

import cats.kernel.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class KakaoAPIService {

    private final String url = "https://dapi.kakao.com/v3/search/book";
    private final String key = "rest api key";

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    private RestTemplate restTemplate;

    @Value("${spring.blog.search.kakao.url}")
    private String kakaoUrl;

    @Value("${spring.blog.search.kakao.rest_api_key}")
    private String kakaoAPIKey;

    public Map getKakaoBlogInfo(String query) {
        try {
            String url = kakaoUrl + "/search/blog";
            this.restTemplate = restTemplateBuilder
                    .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                    .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
//                .additionalInterceptors(new LogRestTemplateInterceptors())
//                .errorHandler(new RestTemplateExceptionHandler())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "KakaoAK "+ kakaoAPIKey);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("query", query);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Map.class).getBody();
        } catch(RestClientException e) {
            e.printStackTrace();
            return new HashMap<>();
        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}
