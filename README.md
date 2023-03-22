# blog-search-service(multi-module)

## 3개 모듈
### common
#### - common entity/vo를 모아둔 module
### search-service(http://localhost:8080)
####  - common 사용
####  - API
##### -- "/search/statistics/keywords" : 키워드 순위 리스트를 statistics-service에 요청
##### -- "/search/blog/paged" : pagination 검색 결과 리스트를 kakao/naver openAPI 요청

### statistics-service(http://localhost:8081)
#### - API(search-service가 사용)
##### -- "/stats/top/list" : 키워드 순위 리스트를 DB에서 조회 반환
##### -- "/stats/registerKeyword" : 사용자 검색 키워드를 DB에 업데이트
