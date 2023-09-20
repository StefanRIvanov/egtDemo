package com.egt.demo.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "command")
public class XmlCriteriaDTO {

    @XmlElementWrapper
    @XmlElement(name = "get")
    public GetElement get;

    @XmlElementWrapper
    @XmlElement(name = "history")
    private History history;

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetElement getGet() {
        return get;
    }

    public void setGet(GetElement get) {
        this.get = get;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public class GetElement {

        @XmlElement(name = "currency")
        private String currency;

        private String consumer;

        public GetElement() {}

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getConsumer() {
            return consumer;
        }

        public void setConsumer(String consumer) {
            this.consumer = consumer;
        }
    }

    public class History {

        private int period;

        private String currency;

        private String consumer;

        public History() {}


        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getConsumer() {
            return consumer;
        }

        public void setConsumer(String consumer) {
            this.consumer = consumer;
        }
    }
}
