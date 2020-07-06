package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.model.SearchData;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SearchDataController {
    private final SearchDataService searchDataService;

    public SearchDataController(SearchDataService searchDataService) {
        this.searchDataService = searchDataService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public SearchData getWebCrawler(@RequestBody SearchData searchData) {
        return searchDataService.getWebCrawler(searchData);
    }
}
