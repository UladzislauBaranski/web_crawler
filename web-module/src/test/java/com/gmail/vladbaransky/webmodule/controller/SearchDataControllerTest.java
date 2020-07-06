package com.gmail.vladbaransky.webmodule.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.model.SearchData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SearchDataController.class)
@AutoConfigureMockMvc
class SearchDataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @MockBean
    private SearchDataService searchDataService;

    @Test
    void getWebCrawlerWithParam_returnStatusOk() throws Exception {
        String contentSearchData = objectMapper.writeValueAsString(getInitialSearchData());
        mockMvc.perform(post("/")
                .contentType(APPLICATION_JSON_VALUE)
                .content(contentSearchData))
                .andExpect(status().isOk());
    }

    @Test
    void getWebCrawler_callBusinessLogic() throws Exception {
        String contentSearchData = objectMapper.writeValueAsString(getInitialSearchData());
        mockMvc.perform(post("/")
                .contentType(APPLICATION_JSON_VALUE)
                .content(contentSearchData))
                .andExpect(status().isOk());
        verify(searchDataService, times(1)).getWebCrawler(eq(getInitialSearchData()));
    }

    @Test
    void getWebCrawler_returnSearchData() throws Exception {
        String contentSearchData = objectMapper.writeValueAsString(getInitialSearchData());

        when(searchDataService.getWebCrawler(getInitialSearchData())).thenReturn(getExpectedSearchData());
        MvcResult result = mockMvc.perform(post("/")
                .contentType(APPLICATION_JSON_VALUE)
                .content(contentSearchData))
                .andReturn();

        verify(searchDataService, times(1)).getWebCrawler(eq(getInitialSearchData()));
        String expectedSearchData = objectMapper.writeValueAsString(getExpectedSearchData());
        String actualReturnedContent = result.getResponse().getContentAsString();
        Assertions.assertThat(expectedSearchData).isEqualTo(actualReturnedContent);
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
}