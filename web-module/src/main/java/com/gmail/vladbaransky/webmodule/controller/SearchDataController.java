package com.gmail.vladbaransky.webmodule.controller;

import com.gmail.vladbaransky.service.util.Exporter;
import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.model.UrlDTO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SearchDataController {
    private final SearchDataService searchDataService;

    public SearchDataController(SearchDataService searchDataService) {
        this.searchDataService = searchDataService;
    }

    @PostMapping
    public List<UrlDTO> getWebCrawler(@RequestParam List<String> urls,
                                      @RequestParam List<String> terms,
                                      @RequestParam Integer visit,
                                      @RequestParam Integer depth) {
        List<UrlDTO> webCrawler = searchDataService.searchDataOnWeb(urls, terms, visit, depth);
        searchDataService.addEntitiesToDb(webCrawler);
        return searchDataService.getEntitiesFroDb();
    }

    @GetMapping
    public List<UrlDTO> getWebCrawler() {
        return searchDataService.getEntitiesFroDb();
    }

    @GetMapping("/export")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=data_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<UrlDTO> urls = searchDataService.getEntitiesFroDb();
        Exporter exporter = new Exporter(urls);
        exporter.export(response);
    }
}
