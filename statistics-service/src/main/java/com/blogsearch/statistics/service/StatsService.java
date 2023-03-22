package com.blogsearch.statistics.service;

import com.blogsearch.common.domain.KeywordCountDTO;
import com.blogsearch.statistics.domain.KeywordCount;
import com.blogsearch.statistics.domain.KeywordCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StatsService {
    @Autowired
    private KeywordCountRepository keywordCountRepository;

    @Transactional
    public KeywordCount getKeywordCounts(String keyword) {
        return keywordCountRepository.findByKeyword(keyword).get();
    }

    @Transactional
    public List<KeywordCount> getTopRatedKeywords() {

        List<KeywordCount> keywordCountList = keywordCountRepository.findMostSearchedList();

        return keywordCountList;
    }
}
