package com.gmail.vladbaransky.service.impl;

import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.model.SearchData;
import com.gmail.vladbaransky.service.util.FileReaderUrl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SearchDataServiceImplTest {

    private SearchDataService searchDataService;

    @Mock
    private FileReaderUrl fileReaderUrl;

    @BeforeEach
    public void setup() {
        this.searchDataService = new SearchDataServiceImpl(fileReaderUrl);
    }

    @Test
    public void addTerm_returnSearchData() {
        List<String> urlList = getUrlList();

        when(fileReaderUrl.getUrlListFromFile()).thenReturn(urlList);
        SearchData returnedSearchData = searchDataService.getWebCrawler(getInitSearchData());
        getAssertion(returnedSearchData, getExpectedSearchData());
    }

    private SearchData getInitSearchData() {
        SearchData searchData = new SearchData();
        List<String> terms = getTerms();

        List<String> urlList = new ArrayList<>();

        List<Map<String, Long>> termAndNumberOfRepetitionsList = new ArrayList<>();
        Map<String, Long> termAndNumberOfRepetitionsMap = new HashMap<>();

        termAndNumberOfRepetitionsList.add(termAndNumberOfRepetitionsMap);

        searchData.setTerms(terms);
        searchData.setUrlList(urlList);
        searchData.setTermAndNumberOfRepetitions(termAndNumberOfRepetitionsList);
        return searchData;
    }

    private SearchData getExpectedSearchData() {
        SearchData searchData = new SearchData();
        List<String> terms = getTerms();
        List<String> urlList = getUrlList();

        List<Map<String, Long>> termAndNumberOfRepetitionsList = getTermAndNumberOfRepetitionsList();
        searchData.setTerms(terms);
        searchData.setUrlList(urlList);
        searchData.setTermAndNumberOfRepetitions(termAndNumberOfRepetitionsList);

        return searchData;
    }

    private List<String> getTerms() {
        List<String> terms = new ArrayList<>();
        terms.add("like");
        return terms;
    }

    private List<String> getUrlList() {
        List<String> urlList = new ArrayList<>();
        urlList.add("https://www.native-english.ru/topics/about-myself");
        return urlList;
    }

    private List<Map<String, Long>> getTermAndNumberOfRepetitionsList() {
        List<Map<String, Long>> termAndNumberOfRepetitionsList = new ArrayList<>();
        Map<String, Long> termAndNumberOfRepetitionsMap = new HashMap<>();
        termAndNumberOfRepetitionsMap.put("like", 12L);
        termAndNumberOfRepetitionsList.add(termAndNumberOfRepetitionsMap);

        return termAndNumberOfRepetitionsList;
    }

    private void getAssertion(SearchData returnedSearchData, SearchData expectedSearchData) {
        assertThat(returnedSearchData).isNotNull();
        assertThat(returnedSearchData.getTerms()).isEqualTo(expectedSearchData.getTerms());
        assertThat(returnedSearchData.getUrlList()).isEqualTo(expectedSearchData.getUrlList());
        assertThat(returnedSearchData.getTermAndNumberOfRepetitions()).isEqualTo(expectedSearchData.getTermAndNumberOfRepetitions());
    }
}