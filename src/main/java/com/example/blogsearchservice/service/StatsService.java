package com.example.blogsearchservice.service;

import com.example.blogsearchservice.domain.KeywordCount;
import com.example.blogsearchservice.domain.KeywordCountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public List<Object[]> getTopRatedKeywords() {
        return keywordCountRepository.findMostSearchedList();
    }

    @Transactional
    public void increaseCount(String keyword) throws Exception {
        try {
            Optional<KeywordCount> entry = keywordCountRepository.findByKeyword(keyword);
            if(entry.isPresent()) {
                keywordCountRepository.incrementCount(keyword);
            } else {
                KeywordCount updateEntry = new KeywordCount(keyword, 1);
                keywordCountRepository.save(updateEntry);
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }
}
