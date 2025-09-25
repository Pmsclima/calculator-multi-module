package com.wit.calculator.service;

import com.wit.calculator.domain.CalculatorBinaryOperands;

import java.math.BigDecimal;

/**
 * Calculator core API service.
 */
public interface CalculatorService {
    /**
     * Calculates the sum of two numbers.
     *
     * @param calculatorBinaryOperands the validated operands to be added.
     *
     * @return sum result.
     */
    BigDecimal sum(final CalculatorBinaryOperands calculatorBinaryOperands);
}