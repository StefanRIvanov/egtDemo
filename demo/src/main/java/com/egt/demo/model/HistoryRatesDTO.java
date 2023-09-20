package com.egt.demo.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public class HistoryRatesDTO {

    @NotNull(message = "The requestId is required.")
    private String requestId;

    @NotNull(message = "The timestamp is required.")
    private Timestamp timestamp;

    @NotEmpty(message = "The client is required.")
    private String client;

    @NotEmpty(message = "The currency is required.")
    private String currency;

    private int period;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }
}
