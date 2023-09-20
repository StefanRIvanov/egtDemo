package com.egt.demo.controller.impl;

import com.egt.demo.controller.XmlController;
import com.egt.demo.model.HistoryRatesDTO;
import com.egt.demo.model.XmlCriteriaDTO;
import com.egt.demo.service.StatisticsCollector;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Map;

@RestController
public class XmlControllerImpl implements XmlController {

    private static final String EXT_SERVICE_1 = "EXT_SERVICE_1";

    private StatisticsCollector statisticsCollector;

    public XmlControllerImpl(StatisticsCollector statisticsCollector) {
        this.statisticsCollector = statisticsCollector;
    }

    @Override
    public ResponseEntity<Map> getBaseData(@Valid @RequestBody XmlCriteriaDTO body) throws Exception {
        if(body.getId() == null) {
            throw new Exception("Id is required attribute of command tag!");
        }

        if(statisticsCollector.existRequestId(body.getId())) {
            throw new Exception("Request ID must be unique!");
        }

        HistoryRatesDTO historyRatesDTO = new HistoryRatesDTO();

        Instant instant = Instant.now();
        historyRatesDTO.setTimestamp(Timestamp.from(instant));
        historyRatesDTO.setRequestId(body.getId());

        if(body.getGet() != null) {
            XmlCriteriaDTO.GetElement getElement = body.getGet();
            if(getElement.getCurrency() == null) {
                throw new Exception("Currency is required tag!");
            }
            if(getElement.getConsumer() == null) {
                throw new Exception("Consumer is required attribute tag!");
            }
            historyRatesDTO.setCurrency(getElement.getCurrency());
            historyRatesDTO.setClient(getElement.getConsumer());

            return ResponseEntity.ok(statisticsCollector.getCurrentBase(historyRatesDTO, EXT_SERVICE_1));
        }

        if(body.getHistory() != null) {
            XmlCriteriaDTO.History historyElement = body.getHistory();
            if(historyElement.getCurrency() == null) {
                throw new Exception("Currency is required tag!");
            }
            if(historyElement.getConsumer() == null) {
                throw new Exception("Consumer is required attribute tag!");
            }
            if(historyElement.getPeriod() == 0) {
                throw new Exception("Period is required attribute tag for this method!");
            }
            historyRatesDTO.setPeriod(historyElement.getPeriod());
            historyRatesDTO.setCurrency(historyElement.getCurrency());
            historyRatesDTO.setClient(historyElement.getConsumer());

            return ResponseEntity.ok(statisticsCollector.getHistoryBase(historyRatesDTO, EXT_SERVICE_1));
        }

        throw new Exception("Payload must contain get or history tags!");
    }
}
