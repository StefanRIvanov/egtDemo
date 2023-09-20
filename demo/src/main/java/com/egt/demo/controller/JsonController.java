package com.egt.demo.controller;

import com.egt.demo.model.HistoryRatesDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/json_api")
public interface JsonController {

    @PostMapping("/current")
    ResponseEntity<Map> getCurrentBase(HistoryRatesDTO body) throws Exception;

    @PostMapping("/history")
    ResponseEntity<Map> getHistoryBase(HistoryRatesDTO body) throws Exception;
}
