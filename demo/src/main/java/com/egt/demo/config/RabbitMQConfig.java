package com.egt.demo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.rates.name}")
    private String ratesQueue;

    @Value("${rabbitmq.queue.history.name}")
    private String historyQueue;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.rates.routing.key}")
    private String routingKeyRates;

    @Value("${rabbitmq.history.routing.key}")
    private String routingKeyHistory;

    @Bean
    public Queue queue() {
        return new Queue(ratesQueue);
    }

    @Bean
    public Queue historyQueue() {
        return new Queue(historyQueue);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    public Binding bindingRates() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(routingKeyRates);
    }

    @Bean
    public Binding bindingHistory() {
        return BindingBuilder
                .bind(historyQueue())
                .to(exchange())
                .with(routingKeyHistory);
    }

}
