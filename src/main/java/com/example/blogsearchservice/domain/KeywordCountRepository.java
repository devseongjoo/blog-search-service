package com.example.blogsearchservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordCountRepository extends JpaRepository<KeywordCount, Long> {

//    @Lock(LockModeType.PESSIMISTIC_READ)
    @Query("UPDATE KeywordCount k set k.count = k.count + 1 WHERE k.keyword = :keyword")
    void incrementCount(@Param("keyword") String keyword);

//    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<KeywordCount> findByKeyword(String keyword);

    @Query(value =
            "SELECT KEYWORD_COUNT, COUNT FROM KEYWORD_COUNT ORDER BY COUNT LIMIT 10",
            nativeQuery = true)
    List<Object[]> findMostSearchedList();
}
