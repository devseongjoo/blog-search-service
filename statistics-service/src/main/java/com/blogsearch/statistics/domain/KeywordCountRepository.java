package com.blogsearch.statistics.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KeywordCountRepository extends JpaRepository<KeywordCount, Long> {

//    @Lock(LockModeType.PESSIMISTIC_READ)
    @Modifying
    @Query("UPDATE KeywordCount k set k.count = k.count + 1 WHERE k.keyword = :keyword")
    void incrementCount(@Param("keyword") String keyword);

//    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<KeywordCount> findByKeyword(String keyword);

    @Query(value =
            "SELECT u FROM KeywordCount k ORDER BY k.count LIMIT 10",
            nativeQuery = true)
    List<KeywordCount> findMostSearchedList();
}
