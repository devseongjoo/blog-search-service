package com.example.blogsearchservice;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class RestTemplateTest {

    @Value("${spring.kakao.common.url}")
    private String kakaoUrl;

    @Value("${spring.kakao.location.search.blog}")
    private String locationSearchBlog;

    @Value("${spring.kakao.location.search.web}")
    private String locationSearchWeb;

    @Value("${spring.kakao.common.rest_api_key}")
    private String kakaoRestAPIKey;

    @Test
    void contextLoads() {

    }

    private RestTemplate restTemplate;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Before
    public void setUp() throws Exception {
        restTemplate = new RestTemplate();
    }

    @Test
    public void test_kakaoSearchBlog() throws Exception {
        String url = "https://dapi.kakao.com/v2/search";

        String searchBlogLoc = "/blog";
        String queryBlog = "https://brunch.co.kr/@tourism 집짓기";

        String searchWebLoc = "/web";
        String queryWeb = "이효리";

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

        //<-- Build URI -->
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(url)
                .path(searchWebLoc)
                .queryParam("query", queryWeb)
                .build();

        //<-- Http Headers -->
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "KakaoAK "+ kakaoRestAPIKey);

        Map<String, String> params = new HashMap<String, String>();
        params.put("query", queryWeb);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        //<-- Check encoded before send-->
        logger.info(builder.toString());
        logger.info(builder.toUri().toString());
        logger.info(builder.toUriString());

        ResponseEntity<Map> response1 = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                Map.class
        );
//
        assertThat(response1.getStatusCodeValue()).isEqualTo("200");
//        //Todo: Documents Length Test
//
////        Map result = restTemplate.exchange(URI, HttpMethod.POST, entity, Map.class).getBody();
//        ResponseEntity<Map> response2 = restTemplate.exchange(
//                url + searchLoc,
//                HttpMethod.GET,
//                entity,
//                Map.class,
//                params
//        );
//
//        assertThat(response2.getStatusCodeValue()).isEqualTo("200");
//        //Todo: Document Length Test
    }

    @Test
    public void test_getFor_uriBuilder() throws Exception {
        //Test
        String URI = "http://localhost:8080/kakao/search";
        String query = "이효리";

        Map<String, String> param = new HashMap<String, String>();
        param.put("query", query);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(URI)
                .path("/{memberId}")
                .queryParam("name","김태연★")
                .encode() //UTF-8 encoding해줌 toUri()로 URI타입을 넘기는 경우 사용
                .buildAndExpand(param);
        Map member = restTemplate.getForObject(builder.toUri(), Map.class);
        //Member member = restTemplate.getForEntity(builder.toUri(),Member.class).getBody();

        logger.info("member : {}",member);
    }

    @Test
    public void test_uri() throws Exception {
        String URI = "한글 포함된 URI";
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(URI).build();
        Map member = restTemplate.getForObject(builder.toString(), Map.class);
        // 이경우 URI 타입을 넘기면 한글을 인코딩 하지 않은 채 전달해서 깨짐
//        Map member = restTemplate.getForObject(builder.toUri(), Map.class);
    }
}
