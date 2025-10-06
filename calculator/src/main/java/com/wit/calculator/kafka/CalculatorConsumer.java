package com.wit.calculator.kafka;

import com.wit.calculator.domain.CalculatorBinaryOperands;
import com.wit.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Kafka consumer that processes calculator events.
 * Delegates directly to the core service.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CalculatorConsumer {
    private final CalculatorService calculatorService;

    /**
     * Consumes a {@link CalculationEvent} message from Kafka and delegates to the core service.
     *
     * @param calculationEvent the calculation event to process.
     */
    @KafkaListener(topics = "${calculator.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void processEvent(final CalculationEvent calculationEvent) {
        final var operands = new CalculatorBinaryOperands(
                new BigDecimal(calculationEvent.firstOperand()),
                new BigDecimal(calculationEvent.secondOperand())
        );

        final var result = switch (calculationEvent.operation()) {
            case "SUM" -> calculatorService.sum(operands);
            case "SUB" -> calculatorService.sub(operands);
            case "MULT" -> calculatorService.mult(operands);
            case "DIV" -> calculatorService.division(operands);
            default -> throw new IllegalArgumentException("Unknown operation: " + calculationEvent.operation());
        };
        log.info("Processed event {}({}, {}) = {}",
                calculationEvent.operation(),
                calculationEvent.firstOperand(),
                calculationEvent.secondOperand(),
                result
        );
    }
}
