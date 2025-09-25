package com.wit.calculator.domain;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CalculatorBinaryOperands(
        @NotNull(message = "{validation.notNull}")
        BigDecimal firstNumber,
        @NotNull(message = "{validation.notNull}")
        BigDecimal secondNumber
) {}