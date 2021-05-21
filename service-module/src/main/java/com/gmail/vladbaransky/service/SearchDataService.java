package com.gmail.vladbaransky.service;

import com.gmail.vladbaransky.service.model.SearchData;
import com.gmail.vladbaransky.service.model.UrlDTO;

import java.util.List;

public interface SearchDataService {
    List<UrlDTO> getWebCrawler(List<UrlDTO> urlDTOList);
}
