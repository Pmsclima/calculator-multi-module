package com.wit.calculator.kafka;

/**
 * Kafka event for calculator operations.
 *
 * @param operation operation to be performed.
 * @param firstOperand first operand as a String.
 * @param secondOperand second operand as a String.
 * @param result result of the operation as a String.
 */
public record CalculationEvent(
        String operation,
        String firstOperand,
        String secondOperand,
        String result
) {}