package com.gmail.vladbaransky.service.converter;


import com.gmail.vladbaransky.repositorymodule.model.Term;
import com.gmail.vladbaransky.repositorymodule.model.Url;
import com.gmail.vladbaransky.service.model.TermDTO;
import com.gmail.vladbaransky.service.model.UrlDTO;

import java.util.LinkedList;
import java.util.List;

public class UrlConverter {
    public static Url getObjectFromDTO(UrlDTO urlDTO) {

        Url url = new Url();
        url.setId(urlDTO.getId());
        url.setUrl(urlDTO.getUrl());
        if (urlDTO.getTermDTOList() != null) {
            for (int i = 0; i < urlDTO.getTermDTOList().size(); i++) {

                Term term = new Term();
                term.setId(urlDTO.getTermDTOList().get(i).getId());
                term.setTerm(urlDTO.getTermDTOList().get(i).getTerm());
                term.setCount(urlDTO.getTermDTOList().get(i).getCount());
                url.getTermList().add(term);
            }
        }
        return url;
    }

    public static UrlDTO getDTOFromObject(Url url) {
        UrlDTO urlDTO = new UrlDTO();

        urlDTO.setId(url.getId());
        urlDTO.setUrl(url.getUrl());


        if (url.getTermList() != null) {
            for (int i = 0; i < url.getTermList().size(); i++) {

                TermDTO termDTO = new TermDTO();
                termDTO.setId(url.getTermList().get(i).getId());
                termDTO.setTerm(url.getTermList().get(i).getTerm());
                termDTO.setCount(url.getTermList().get(i).getCount());
                urlDTO.getTermDTOList().add(termDTO);
            }
        }
        return urlDTO;
    }
}
