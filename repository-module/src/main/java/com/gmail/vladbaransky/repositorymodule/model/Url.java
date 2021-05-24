package com.gmail.vladbaransky.repositorymodule.model;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "urls")
public class Url implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "url")
    private String url;

    @ManyToMany(fetch = FetchType.LAZY, cascade =CascadeType.ALL)
    @JoinTable(name = "urls_terms", joinColumns = @JoinColumn(name = "url_id"),
            inverseJoinColumns = @JoinColumn(name = "term_id"))
    private List<Term> termList = new LinkedList<>();

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

    public List<Term> getTermList() {
        return termList;
    }

    public void setTermList(List<Term> termList) {
        this.termList = termList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url1 = (Url) o;
        return Objects.equals(id, url1.id) &&
                Objects.equals(url, url1.url) &&
                Objects.equals(termList, url1.termList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, termList);
    }
}
