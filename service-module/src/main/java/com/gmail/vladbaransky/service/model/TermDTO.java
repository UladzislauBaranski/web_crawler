package com.gmail.vladbaransky.service.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class TermDTO implements Serializable {
    private Long id;
    private String term;
    private Long count;
    private List<UrlDTO> urlDTOList = new LinkedList<>();

    public TermDTO() {
    }

    public TermDTO(String term) {
        this.term = term;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermDTO termDTO = (TermDTO) o;
        return Objects.equals(id, termDTO.id) &&
                Objects.equals(term, termDTO.term) &&
                Objects.equals(count, termDTO.count) &&
                Objects.equals(urlDTOList, termDTO.urlDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, term, count, urlDTOList);
    }

    @Override
    public String toString() {
        return "TermDTO{" +
                "id=" + id +
                ", term='" + term + '\'' +
                ", count=" + count +
                ", urlDTOList=" + urlDTOList +
                '}';
    }
}

