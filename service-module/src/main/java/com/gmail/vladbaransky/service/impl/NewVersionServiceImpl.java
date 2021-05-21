package com.gmail.vladbaransky.service.impl;

import com.gmail.vladbaransky.repositorymodule.SearchDataRepository;
import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.model.TermDTO;
import com.gmail.vladbaransky.service.model.UrlDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

import static com.gmail.vladbaransky.service.constant.RegexpConstant.STRING_BY_WORD_REGEXP;

@Service
public class NewVersionServiceImpl implements SearchDataService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final SearchDataRepository searchDataRepository;

    public NewVersionServiceImpl(SearchDataRepository searchDataRepository) {
        this.searchDataRepository = searchDataRepository;
    }

    @Override
    public List<UrlDTO> getWebCrawler(List<UrlDTO> urlDTOList) {
List<UrlDTO> newUrlList= new ArrayList<>();
        for (int i = 0; i < urlDTOList.size(); i++) {

            UrlDTO urlDTO = urlDTOList.get(i);
            UrlDTO changeUrlDTO = findTermsInWebsite(urlDTO);
            newUrlList.add(changeUrlDTO);
        }
        return newUrlList;
    }


    private UrlDTO findTermsInWebsite(/*String term, String urlString*/UrlDTO urlDTO) {
        String urlName = urlDTO.getUrl();
        List<TermDTO> termDTOList = urlDTO.getTermDTOList();
        Long count = 0L;
        Map<String, Long> map = new TreeMap<>();
        try {
            URL url = new URL(urlName);
            try {
                LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
                String string = reader.readLine();
                while (string != null) {
                    for (int i = 0; i < termDTOList.size(); i++) {
                        String term = termDTOList.get(i).getTerm();
                        boolean result = string.contains(term);
                        if (result) {
                            Long termsByString = findTermsByString(string, term);
                            urlDTO.getTermDTOList().get(i).setCount(termsByString);
                        }
                        string = reader.readLine();
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.getMessage();
            }

        } catch (MalformedURLException ex) {
            ex.getMessage();
        }
        return urlDTO;
    }

    private Long findTermsByString(String string, String term) {
        Long count = 0L;
        String[] StringByWords = string.split(STRING_BY_WORD_REGEXP);
        for (String word : StringByWords) {
            if (word.equals(term)) {
                count++;
            }
        }
        return count;
    }
}
