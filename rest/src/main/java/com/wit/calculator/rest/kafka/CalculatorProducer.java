package com.wit.calculator.rest.kafka;

import com.wit.calculator.kafka.CalculationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Kafka producer responsible for publishing calculator events to a topic.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CalculatorProducer {
    private final KafkaTemplate<String, CalculationEvent> kafkaTemplate;
    @Value("${calculator.topic}")
    private String topic;

    /**
     * Publishes a message to the configured topic.
     *
     * @param calculationEvent JSON payload describing the event.
     */
    public void send(final CalculationEvent calculationEvent) {
        log.info("Publishing message to topic {}: {}",topic, calculationEvent);
        kafkaTemplate.send(topic, calculationEvent);
    }
}