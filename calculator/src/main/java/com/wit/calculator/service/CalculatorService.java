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

    /**
     * Calculates the subtraction of two numbers.
     *
     * @param calculatorBinaryOperands the validated operands to be added.
     *
     * @return sub result.
     */
    BigDecimal sub(final CalculatorBinaryOperands calculatorBinaryOperands);

    /**
     * Calculates the multiplication of two numbers.
     *
     * @param calculatorBinaryOperands the validated operands to be added.
     *
     * @return multiplication result.
     */
    BigDecimal mult(final CalculatorBinaryOperands calculatorBinaryOperands);
}