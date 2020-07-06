package com.gmail.vladbaransky.webmodule.controller.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.service.model.SearchData;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SearchDataControllerITTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getWebCrawler() throws Exception {
        String initSearchData = objectMapper.writeValueAsString(getInitialSearchData());

        mockMvc.perform(post("/")
                .contentType(APPLICATION_JSON_VALUE)
                .content(initSearchData))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("terms", is(getExpectedSearchData().getTerms())).exists())
                .andExpect(jsonPath("urlList", is(getExpectedSearchData().getUrlList())).exists())
                .andExpect(jsonPath("termAndNumberOfRepetitions", is(getExpectedSearchData().getTermAndNumberOfRepetitions())).exists());
    }

    private SearchData getInitialSearchData() {
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
}