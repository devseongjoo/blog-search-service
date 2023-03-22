package com.blogsearch.search.service;

import com.blogsearch.common.domain.KeywordCountDTO;
import com.blogsearch.common.domain.RequestVO;
import com.blogsearch.common.domain.ResponseVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

@Service
public class RestTemplateService {

    //Kakao OpenAPI 관련 props
    @Value("${spring.kakao.URL}")
    private String KAKAO_URL;
    @Value("${spring.kakao.LOCATION}")
    private String KAKAO_LOCATION_BLOG;
    @Value("${spring.kakao.LOCATION-WEB}")
    private String KAKAO_LOCATION_WEB;
    @Value("${spring.kakao.REST_API_KEY}")
    private String KAKAO_REST_API_KEY;

    //Naver OpenAPI 관련 props
    @Value("${spring.naver.URL}")
    private String NAVER_URL;
    @Value("${spring.naver.LOCATION}")
    private String NAVER_LOCATION_BLOG;
    @Value("${spring.naver.CLIENT_ID}")
    private String NAVER_CLIENT_ID;
    @Value("${spring.naver.CLIENT_SECRET")
    private String NAVER_CLIENT_SECRET;

    @Value("${spring.statistics.URL}")
    private String STATISTICS_URL;

    private Logger logger = LoggerFactory.getLogger(getClass());
    private RestTemplate restTemplate;

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public ResponseEntity<ResponseVO> fetchKakaoOpenAPI(RequestVO requestVO) throws Exception {
        try {
            String location = KAKAO_LOCATION_BLOG;

            this.restTemplate = restTemplateBuilder.build();

            //<-- Build URI -->
            UriComponents builder = UriComponentsBuilder.fromHttpUrl(KAKAO_URL)
                    .path(location)
                    .queryParam("query", requestVO.getQuery())
                    .queryParam("sort", requestVO.getSort())
                    .queryParam("page", requestVO.getPage())
                    .queryParam("size", requestVO.getSize())
                    .build();

            //<-- Http Headers -->
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);

            HttpEntity<String> entity = new HttpEntity<String>(headers);

            //<-- Check encoded before send-->
            logger.info(builder.toString());
            logger.info(builder.toUri().toString());
            logger.info(builder.toUriString());

            //<-- request -->
            ResponseEntity<ResponseVO> response = restTemplate.exchange(
                    builder.toUriString(),
                    HttpMethod.GET,
                    entity,
                    ResponseVO.class
            );

            //<-- handle response -->
            if (response.getStatusCode().value() == 200) {
                return response;
            }

        } catch (HttpClientErrorException e) {
            //<-- handle http client related errors -->
            logger.error(e.getStatusText());
            logger.error(e.getResponseBodyAsString());
        } catch (HttpServerErrorException e) {
            logger.error(e.getStatusText());
            logger.error(e.getResponseBodyAsString());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }

        return null;
    }

    public String fetchNaverOpenAPI(String query) {
        String clientId = NAVER_CLIENT_ID;
        String clientSecret = NAVER_CLIENT_SECRET;//애플리케이션 클라이언트 시크릿값";

        //<-- encode query -->
        String encoded = null;
        try {
            encoded = URLEncoder.encode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        if (encoded != null) {
            try {
                String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + encoded;
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
                int responseCode = con.getResponseCode();
                BufferedReader br;
                if (responseCode == 200) {
                    // 정상 호출
                    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                } else {
                    // 에러 발생
                    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                }
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }
                br.close();
                System.out.println(response.toString());

                return response.toString();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        return null;
    }

    public ResponseEntity<KeywordCountDTO> updateKeyword(String query) {
        try {
            restTemplate = restTemplateBuilder.build();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            KeywordCountDTO keywordCountDTO = new KeywordCountDTO();
            keywordCountDTO.setKeyword(query);

            HttpEntity<KeywordCountDTO> request = new HttpEntity<>(keywordCountDTO, headers);

            ResponseEntity<KeywordCountDTO> response = restTemplate.postForEntity(
                    STATISTICS_URL + "/stats/registerKeyword", request, KeywordCountDTO.class);

            return response;

        } catch (HttpStatusCodeException e) {
            logger.error(e.toString());
        }

        return null;
    }

    public ResponseEntity<List<KeywordCountDTO>> getTopRatedList() {
        URI uri = UriComponentsBuilder                //주소를 만들떄 : UriComponentBuilder를 사용
                .fromUriString(STATISTICS_URL)
                .path("/stats/top/list")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplateBuilder().build();
        try {
            ResponseEntity<List<KeywordCountDTO>> response = restTemplate.exchange(uri,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<KeywordCountDTO>>() {
                    });

            return response;

        } catch (HttpStatusCodeException e) {
            return new ResponseEntity<List<KeywordCountDTO>>(e.getStatusCode());
        }
    }
}
