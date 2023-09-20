package com.egt.demo.controller.impl;

import com.egt.demo.controller.JsonController;
import com.egt.demo.model.HistoryRatesDTO;
import com.egt.demo.service.StatisticsCollector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class JsonControllerImpl implements JsonController {

    private static final String EXT_SERVICE_2 = "EXT_SERVICE_2";

    private StatisticsCollector statisticsCollector;

    public JsonControllerImpl(StatisticsCollector statisticsCollector) {
        this.statisticsCollector = statisticsCollector;
    }

    @Override
    public ResponseEntity<Map> getCurrentBase(@Valid @RequestBody HistoryRatesDTO body) throws Exception {
        if(statisticsCollector.existRequestId(body.getRequestId().toString())) {
            throw new Exception("Request ID must be unique!");
        }

        return ResponseEntity.ok(statisticsCollector.getCurrentBase(body, EXT_SERVICE_2));
    }

    @Override
    public ResponseEntity<Map> getHistoryBase(@Valid @RequestBody HistoryRatesDTO body) throws Exception {
        if(statisticsCollector.existRequestId(body.getRequestId().toString())) {
            throw new Exception("Request ID must be unique!");
        }
        if(body.getPeriod() == 0) {
            throw new Exception("Period is mandatory field for this request!");
        }

        return ResponseEntity.ok(statisticsCollector.getHistoryBase(body, EXT_SERVICE_2));
    }
}