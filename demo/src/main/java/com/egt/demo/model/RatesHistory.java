package com.egt.demo.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Type;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "request_history")
public class RatesHistory {

    @Id
    private String requestId;

    @Column
    private String serviceName;

    @Column
    private Timestamp time;

    @Column(name = "client_id")
    private String clientId;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}
