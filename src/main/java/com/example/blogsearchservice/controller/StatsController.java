package com.example.blogsearchservice.controller;

import com.example.blogsearchservice.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/stats")
public class StatsController {

    @Autowired
    StatsService statsService;

    @GetMapping(value = "/readTopRatedList")
    public List<Object[]> getTopRatedList() {
        return statsService.getTopRatedKeywords();
    }
}
