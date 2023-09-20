package com.egt.demo.service.impl;

import com.egt.demo.dao.RatesDao;
import com.egt.demo.dao.RatesHistoryDao;
import com.egt.demo.model.HistoryRatesDTO;
import com.egt.demo.model.RatesHistory;
import com.egt.demo.service.StatisticsCollector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatisticsCollectorImpl implements StatisticsCollector {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticsCollectorImpl.class);

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.history.routing.key}")
    private String routingKey;

    private RatesHistoryDao ratesHistoryDao;
    private RatesDao ratesDao;
    private RabbitTemplate rabbitTemplate;
    private ObjectMapper objectMapper;

    public StatisticsCollectorImpl(RatesHistoryDao ratesHistoryDao, RatesDao ratesDao, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.ratesHistoryDao = ratesHistoryDao;
        this.ratesDao = ratesDao;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map getCurrentBase(HistoryRatesDTO body, String serviceName) throws JsonProcessingException {
        saveHistory(body, serviceName);

        Map data = ratesDao.getLatestByCurrency(body.getCurrency());

        Map result = new HashMap();
        result.put("rate", data.get("rate"));
        result.put("client", body.getClient());
        result.put("currency", body.getCurrency());

        return result;
    }

    @Override
    public boolean existRequestId(String requestId) {
        RatesHistory ratesHistory = ratesHistoryDao.get(requestId);
        if(ratesHistory != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Map getHistoryBase(HistoryRatesDTO body, String serviceName) throws JsonProcessingException {
        saveHistory(body, serviceName);

        List data = ratesDao.getRatesByPeriodAndCurrency(body.getPeriod(), body.getCurrency());

        Map result = new HashMap();
        result.put("history_rate", data);
        result.put("client", body.getClient());
        result.put("currency", body.getCurrency());

        return result;
    }

    private void sendMessageToRabbitMQ(Object message) {
        LOGGER.info(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

    private void saveHistory(HistoryRatesDTO body, String serviceName) throws JsonProcessingException {
        RatesHistory ratesHistory = new RatesHistory();
        ratesHistory.setClientId(body.getClient());
        ratesHistory.setServiceName(serviceName);
        ratesHistory.setRequestId(body.getRequestId().toString());
        ratesHistory.setTime(body.getTimestamp());
        ratesHistoryDao.save(ratesHistory);

        sendMessageToRabbitMQ(objectMapper.writeValueAsString(ratesHistory));
    }
}
