package com.blogsearch.statistics.controller;

import com.blogsearch.common.domain.KeywordCountDTO;
import com.blogsearch.statistics.domain.KeywordCount;
import com.blogsearch.statistics.domain.KeywordCountRepository;
import com.blogsearch.statistics.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    StatsService statsService;

    @Autowired
    KeywordCountRepository keywordCountRepository;

    @GetMapping(value = "/top/list")
    public ResponseEntity<List<KeywordCountDTO>> getTopRatedList() {

        List<KeywordCount> keywordCountList = statsService.getTopRatedKeywords();

        if(keywordCountList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<KeywordCountDTO> keywordCountDTOList = new ArrayList<KeywordCountDTO>();
        keywordCountList.forEach((keywordCount -> {
            keywordCountDTOList.add(KeywordCountDTO.builder()
                    .keyword(keywordCount.getKeyword())
                    .count(keywordCount.getCount())
                    .build()
            );
        }));

        return new ResponseEntity<List<KeywordCountDTO>>(keywordCountDTOList, HttpStatus.OK);
    }

    @PostMapping(value = "/registerKeyword")
    @Transactional
    public ResponseEntity<Object> updateKeyword(@RequestBody KeywordCountDTO keywordCountDTO) throws Exception {

        Optional<KeywordCount> keywordCountOptional = keywordCountRepository.findByKeyword(keywordCountDTO.getKeyword());

        if(keywordCountOptional.isPresent()) {
            KeywordCount keywordCount = keywordCountOptional.get();
            keywordCount.setCount(keywordCount.getCount() + 1);
        } else {
            KeywordCount updateEntry = new KeywordCount(keywordCountDTO.getKeyword(), 1);
            keywordCountRepository.save(updateEntry);
        }

        return ResponseEntity.ok().build();
    }
}
