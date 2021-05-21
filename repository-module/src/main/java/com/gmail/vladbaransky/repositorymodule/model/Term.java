package com.gmail.vladbaransky.repositorymodule.model;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

public class Term {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "term")
    private String term;
    @Column(name = "count")
    private Long count;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "urls", joinColumns = @JoinColumn(name = "term_id"),
            inverseJoinColumns = @JoinColumn(name = "url_id"))
    private List<Url> urlList = new LinkedList<>();

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

    public List<Url> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<Url> urlList) {
        this.urlList = urlList;
    }
}
