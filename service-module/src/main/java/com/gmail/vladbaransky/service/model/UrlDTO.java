package com.gmail.vladbaransky.service.model;



import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class UrlDTO implements Serializable {
    private Long id;
   private String url;
   private List<TermDTO> termDTOList = new LinkedList<>();

    public UrlDTO() {
    }

    public UrlDTO(String url, List<TermDTO> termDTOList) {
        this.url = url;
        this.termDTOList = termDTOList;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UrlDTO urlDTO = (UrlDTO) o;
        return Objects.equals(id, urlDTO.id) &&
                Objects.equals(url, urlDTO.url) &&
                Objects.equals(termDTOList, urlDTO.termDTOList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, termDTOList);
    }

    @Override
    public String toString() {
        return "UrlDTO{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", termDTOList=" + termDTOList +
                '}';
    }
}
