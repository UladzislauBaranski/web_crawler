package com.gmail.vladbaransky.service.model;

import java.util.LinkedList;
import java.util.List;

public class TermDTO {
    private Long id;
    private String term;
    private Long count;
    private List<UrlDTO> urlDTOList = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<UrlDTO> getUrlDTOList() {
        return urlDTOList;
    }

    public void setUrlDTOList(List<UrlDTO> urlDTOList) {
        this.urlDTOList = urlDTOList;
    }
}

