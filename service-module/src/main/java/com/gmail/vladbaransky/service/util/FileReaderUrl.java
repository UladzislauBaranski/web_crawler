package com.gmail.vladbaransky.service.util;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.gmail.vladbaransky.service.constant.FilePathConstant.INPUT_FILE_PATH;
import static com.gmail.vladbaransky.service.constant.RegexpConstant.URL_REGEXP;

@Component
public class FileReaderUrl {
    public List<String> getUrlListFromFile() {
        List<String> urlList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                urlList.addAll(Arrays.asList(line.split(URL_REGEXP)));
            }
        } catch (IOException e) {
            e.getMessage();
        }
        return urlList;
    }
}
