package com.wit.calculator.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/**
 * Payload for calculator requests.
 *
 * @param firstNumber the first number to be added.
 * @param secondNumber the second number to be added.
 */
@Schema(description = "Payload body for calculator operations with two operands requests.")
public record CalculatorRequest(
        @NotNull(message = "{validation.notNull}")
        @Schema(description = "The first operand.", example = "10.5")
        BigDecimal firstNumber,
        @NotNull(message = "{validation.notNull}")
        @Schema(description = "The second operand.", example = "5.5")
        BigDecimal secondNumber
) {}