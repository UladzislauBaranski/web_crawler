package com.gmail.vladbaransky.service;

import com.gmail.vladbaransky.service.model.UrlDTO;

import java.util.List;

public interface SearchDataService {
    List<UrlDTO> searchDataOnWeb(List<String> urls, List<String> terms, Integer visit, Integer depth);

    void addEntitiesToDb(List<UrlDTO> urlDTOList);

    List<UrlDTO> getEntitiesFroDb();
}
