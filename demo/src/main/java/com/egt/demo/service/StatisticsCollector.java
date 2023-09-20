package com.egt.demo.service;

import com.egt.demo.model.HistoryRatesDTO;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

public interface StatisticsCollector {

    Map getCurrentBase(HistoryRatesDTO body, String serviceName) throws JsonProcessingException;

    boolean existRequestId(String requestId);

    Map getHistoryBase(HistoryRatesDTO body, String serviceName) throws JsonProcessingException;
}
