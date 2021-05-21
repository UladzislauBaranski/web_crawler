package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.model.SearchData;
import com.gmail.vladbaransky.service.model.UrlDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class SearchDataController {
    private final SearchDataService searchDataService;

    public SearchDataController(SearchDataService searchDataService) {
        this.searchDataService = searchDataService;
    }

    @GetMapping()
    public String getWebCrawler() {
        return "hello";
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<UrlDTO> getWebCrawler(@RequestBody List<UrlDTO> urlDTOList) {
        return searchDataService.getWebCrawler(urlDTOList);
    }
}
