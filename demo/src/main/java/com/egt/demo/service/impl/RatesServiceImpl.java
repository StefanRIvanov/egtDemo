package com.egt.demo.service.impl;

import com.egt.demo.dao.RatesDao;
import com.egt.demo.model.Rates;
import com.egt.demo.service.RatesService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
public class RatesServiceImpl implements RatesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RatesServiceImpl.class);

    private static final String VALUE_RATES = "rates";
    private static final String VALUE_BASE = "base";
    private static final String PRIVATE_KEY = "4e558e41a5814d0498bf94b44ba66bee";

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.rates.routing.key}")
    private String routingKey;

    private RabbitTemplate rabbitTemplate;
    private RatesDao ratesDao;
    private ObjectMapper objectMapper;

    public RatesServiceImpl(RabbitTemplate rabbitTemplate, RatesDao ratesDao, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.ratesDao = ratesDao;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save() throws IOException, JSONException {

        OkHttpClient client = new OkHttpClient().newBuilder().build();

        Request request = new Request.Builder()
                .url(String.format("http://data.fixer.io/api/latest?access_key=%s&format=1", PRIVATE_KEY))
                .method("GET", null)
                .build();

        Response response = client.newCall(request).execute();
        String jsonResponse = response.body().string();
        JSONObject object = new JSONObject(jsonResponse);

        Rates rates = new Rates();
        rates.setRate(object.getJSONObject(VALUE_RATES).toString());
        rates.setCurrency(object.get(VALUE_BASE).toString());
        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        rates.setUpdatedAt(timestamp);

        sendMessageToRabbitMQ(objectMapper.writeValueAsString(rates));
        ratesDao.save(rates);
    }

    private void sendMessageToRabbitMQ(Object message) {
        LOGGER.info(String.format("Message sent -> %s", message));
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
