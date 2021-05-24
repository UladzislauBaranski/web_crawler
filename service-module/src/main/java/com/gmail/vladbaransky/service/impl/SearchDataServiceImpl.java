package com.gmail.vladbaransky.service.impl;

import com.gmail.vladbaransky.repositorymodule.SearchDataRepository;
import com.gmail.vladbaransky.repositorymodule.model.Url;
import com.gmail.vladbaransky.service.SearchDataService;
import com.gmail.vladbaransky.service.converter.UrlConverter;
import com.gmail.vladbaransky.service.model.TermDTO;
import com.gmail.vladbaransky.service.model.UrlDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Text;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Transactional
public class SearchDataServiceImpl implements SearchDataService {
    private static final String REGEX = "[' ']";
    private static final String MAX_VISIT_MESSAGE = "Max visited pages more than";
    private static final String LINK_DEPTH_MESSAGE = "Link depth more than";
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private final SearchDataRepository searchDataRepository;
    private long countTransition = 0;

    public SearchDataServiceImpl(SearchDataRepository searchDataRepository) {
        this.searchDataRepository = searchDataRepository;
    }

    @Override
    public List<UrlDTO> searchDataOnWeb(List<String> urls, List<String> terms, Integer visit, Integer depth) {
        List<UrlDTO> newUrlList = new ArrayList<>();
        for (String url : urls) {
            if (checkLinkDepth(url, depth)) {
                if (checkCountTransition() <= visit) {
                    List<TermDTO> ts = new ArrayList<>();
                    for (String term : terms) {
                        TermDTO termDTO = new TermDTO(term);
                        ts.add(termDTO);
                    }
                    UrlDTO urlDTO = new UrlDTO(url, ts);
                    UrlDTO result = findTermsInWebsite(urlDTO);
                    newUrlList.add(result);
                } else logger.info(MAX_VISIT_MESSAGE + visit);
            } else logger.info(LINK_DEPTH_MESSAGE + depth);
        }
        return newUrlList;
    }

    private UrlDTO findTermsInWebsite(UrlDTO urlDTO) {
        String urlName = urlDTO.getUrl();
        List<TermDTO> termDTOList = urlDTO.getTermDTOList();
        for (int i = 0; i < termDTOList.size(); i++) {
            try {
                URL url = new URL(urlName);
                try {
                    LineNumberReader reader = new LineNumberReader(new InputStreamReader(url.openStream()));
                    Long count = 0L;
                    String term = termDTOList.get(i).getTerm();
                    String regex = "" + term + "";
                    Pattern regexp = Pattern.compile(regex);
                    String string = reader.readLine();
                    while (string != null) {
                        Matcher m = regexp.matcher(string);
                        if (m.find()) {
                            String[] split = string.split(REGEX);
                            for (String s : split) {

                                Matcher match = regexp.matcher(s);
                                while (match.find()) {
                                    count++;
                                }
                            }
                        }
                        string = reader.readLine();
                    }
                    reader.close();
                    urlDTO.getTermDTOList().get(i).setCount(count);
                } catch (IOException e) {
                    e.getMessage();
                }

            } catch (MalformedURLException ex) {
                ex.getMessage();
            }
        }
        return urlDTO;
    }

    @Override
    public void addEntitiesToDb(List<UrlDTO> urlDTOList) {
        List<Url> urlList = urlDTOList.stream().map(UrlConverter::getObjectFromDTO).collect(Collectors.toList());
        searchDataRepository.addObject(urlList);
    }

    @Override
    public List<UrlDTO> getEntitiesFroDb() {
        List<Url> urlList = searchDataRepository.getAllObject();
        return urlList.stream().map(UrlConverter::getDTOFromObject).collect(Collectors.toList());
    }

    private boolean checkLinkDepth(String url, Integer depth) {
        String[] split = url.split("/");
        return split.length <= depth;
    }

    private long checkCountTransition() {
        countTransition++;
        return countTransition;
    }


}
