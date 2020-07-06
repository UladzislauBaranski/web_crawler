package com.gmail.vladbaransky.service.impl;

import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.model.SearchData;
import com.gmail.vladbaransky.service.util.FileReaderUrl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.gmail.vladbaransky.service.constant.Constant.*;
import static com.gmail.vladbaransky.service.constant.FilePathConstant.OUTPUT_FILE_PATH;
import static com.gmail.vladbaransky.service.constant.RegexpConstant.STRING_BY_WORD_REGEXP;

@Service
public class SearchDataServiceImpl implements SearchDataService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final FileReaderUrl fileReaderUrl;

    public SearchDataServiceImpl(FileReaderUrl fileReaderUrl) {
        this.fileReaderUrl = fileReaderUrl;
    }

    @Override
    public SearchData getWebCrawler(SearchData searchData) {
        List<String> urlList = fileReaderUrl.getUrlListFromFile();
        Map<String, Long> termAndNumberOfRepetitions;
        List<Map<String, Long>> mapList = new ArrayList<>();

        List<Map<String, Long>> termAndNumberOfRepetitionsList = new ArrayList<>();
        Map<String, Long> termAndNumberOfRepetitionsMap = new HashMap<>();
        termAndNumberOfRepetitionsList.add(termAndNumberOfRepetitionsMap);

        searchData.setUrlList(urlList);
        searchData.setTermAndNumberOfRepetitions(termAndNumberOfRepetitionsList);
        for (String url : searchData.getUrlList()) {
            for (String term : searchData.getTerms()) {
                termAndNumberOfRepetitions = findTermsInWebsite(term, url);
                mapList.add(termAndNumberOfRepetitions);
            }
        }
        searchData.setTermAndNumberOfRepetitions(mapList);
        writeToConsole(searchData);
        writeToFile(searchData);
        return searchData;
    }

    private Map<String, Long> findTermsInWebsite(String term, String urlString) {
        Long count = 0L;
        Map<String, Long> map = new TreeMap<>();
        try {
            URL url = new URL(urlString);
            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
                String string = reader.readLine();
                while (string != null) {
                    boolean result = string.contains(term);
                    if (result) {
                        map = findTermsByString(string, term);
                        Set<String> terms = map.keySet();
                        for (String termKey : terms) {
                            count += map.get(termKey);
                            map.put(termKey, count);
                        }
                    }
                    string = reader.readLine();
                }
                reader.close();
            } catch (IOException e) {
                e.getMessage();
            }
        } catch (MalformedURLException ex) {
            ex.getMessage();
        }
        Map<String, Long> emptyMap = new TreeMap<>();
        emptyMap.put(term, 0L);
        if (!map.isEmpty()) {
            return map;
        } else {
            return emptyMap;
        }
    }

    private Map<String, Long> findTermsByString(String string, String term) {
        Map<String, Long> mapTermAndNumberOfRepetitions = new HashMap<>();
        mapTermAndNumberOfRepetitions.put(term, 0L);
        Map<String, Long> map = new HashMap<>();
        List<String> StringByWords = new ArrayList<>();
        StringByWords.addAll(Arrays.asList(string.split(STRING_BY_WORD_REGEXP)));
        for (String word : StringByWords) {
            if (word.equals(term)) {
                mapTermAndNumberOfRepetitions = countDuplicates(word, map);
            }
        }
        return mapTermAndNumberOfRepetitions;
    }

    private Map<String, Long> countDuplicates(String word, Map<String, Long> map) {
        map.merge(word, 1L, Long::sum);
        return map;
    }

    private void writeToFile(SearchData searchData) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUTPUT_FILE_PATH))) {
            bufferedWriter.write(HEAD);
            bufferedWriter.write(System.lineSeparator());
            int j = 0;
            for (int i = 0; i < searchData.getUrlList().size(); i++) {
                for (j = j; j < searchData.getTerms().size() * (i + 1); j++) {
                    bufferedWriter.write(String.valueOf(searchData.getTermAndNumberOfRepetitions().get(j).values()));
                }
                bufferedWriter.write(SEPARATOR);
                bufferedWriter.write(String.valueOf(searchData.getTerms()));
                bufferedWriter.write(SEPARATOR);
                bufferedWriter.write(String.valueOf(searchData.getUrlList().get(i)));
                bufferedWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    private void writeToConsole(SearchData searchData) {
        List<Collection<Long>> mapValuesList = new ArrayList<>();
        logger.info(SEPARATOR_LINE);
        logger.info(HEAD);
        int j = 0;
        for (int i = 0; i < searchData.getUrlList().size(); i++) {
            for (j = j; j < searchData.getTerms().size() * (i + 1); j++) {
                mapValuesList.add(searchData.getTermAndNumberOfRepetitions().get(j).values());
            }
            logger.info(mapValuesList + SEPARATOR_SPACE + searchData.getTerms() + SEPARATOR_SPACE + searchData.getUrlList().get(i));
            mapValuesList.clear();
        }
    }
}
