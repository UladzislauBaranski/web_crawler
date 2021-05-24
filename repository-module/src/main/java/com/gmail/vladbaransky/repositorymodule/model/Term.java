package com.gmail.vladbaransky.repositorymodule.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "terms")
public class Term implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "term")
    private String term;
    @Column(name = "count")
    private Long count;

    @ManyToMany(mappedBy = "termList")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term term1 = (Term) o;
        return Objects.equals(id, term1.id) &&
                Objects.equals(term, term1.term) &&
                Objects.equals(count, term1.count) &&
                Objects.equals(urlList, term1.urlList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, term, count, urlList);
    }
}
