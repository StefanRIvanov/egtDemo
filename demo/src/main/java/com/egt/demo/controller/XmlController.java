package com.egt.demo.controller;

import com.egt.demo.model.XmlCriteriaDTO;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/xml_api")
public interface XmlController {

    @PostMapping(path = "/command", consumes = MediaType.APPLICATION_XML_VALUE)
    ResponseEntity<Map> getBaseData(XmlCriteriaDTO body) throws Exception;
}
