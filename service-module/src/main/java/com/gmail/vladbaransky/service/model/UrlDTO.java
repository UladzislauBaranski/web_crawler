package com.gmail.vladbaransky.service.model;

import java.util.LinkedList;
import java.util.List;

public class UrlDTO {
    private Long id;
   private String url;
   private List<TermDTO> termDTOList = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<TermDTO> getTermDTOList() {
        return termDTOList;
    }

    public void setTermDTOList(List<TermDTO> termDTOList) {
        this.termDTOList = termDTOList;
    }
}
