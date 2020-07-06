package com.gmail.vladbaransky.service.model;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.gmail.vladbaransky.service.constant.validation.ValidationMessages.NOT_EMPTY_MESSAGE;

public class SearchData {
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    private List<String> terms;
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    private List<String> urlList;
    @NotEmpty(message = NOT_EMPTY_MESSAGE)
    private List<Map<String, Long>> termAndNumberOfRepetitions;

    public List<String> getTerms() {
        return terms;
    }

    public void setTerms(List<String> terms) {
        this.terms = terms;
    }

    public List<String> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<String> urlList) {
        this.urlList = urlList;
    }

    public List<Map<String, Long>> getTermAndNumberOfRepetitions() {
        return termAndNumberOfRepetitions;
    }

    public void setTermAndNumberOfRepetitions(List<Map<String, Long>> termAndNumberOfRepetitions) {
        this.termAndNumberOfRepetitions = termAndNumberOfRepetitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchData that = (SearchData) o;
        return Objects.equals(terms, that.terms) &&
                Objects.equals(urlList, that.urlList) &&
                Objects.equals(termAndNumberOfRepetitions, that.termAndNumberOfRepetitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(terms, urlList, termAndNumberOfRepetitions);
    }

    @Override
    public String toString() {
        return "SearchData{" +
                "terms=" + terms +
                ", urlList=" + urlList +
                ", termAndNumberOfRepetitions=" + termAndNumberOfRepetitions +
                '}';
    }
}
