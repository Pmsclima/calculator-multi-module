package com.wit.calculator.rest.kafka;

import com.wit.calculator.kafka.CalculationEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.kafka.core.*;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.verify;

@TestPropertySource(properties = "calculator.topic=calculator.events")
@SpringBootTest(classes = CalculatorProducer.class)
class CalculatorProducerSmokeTest {
    @Autowired
    CalculatorProducer producer;
    @MockBean
    private KafkaTemplate<String, CalculationEvent> kafkaTemplate;

    @Test
    void sendsToTopic() {
        CalculationEvent calculationEvent = new CalculationEvent(
                "SUM",
                "3",
                "7",
                "10"
        );

        producer.send(calculationEvent);

        verify(kafkaTemplate).send("calculator.events", calculationEvent);
    }
}