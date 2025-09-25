package com.wit.calculator.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Payload for calculator responses.
 *
 * @param result the result of the calculation.
 */
public record CalculatorResponse(
        @Schema(description = "The result of the calculation.", example = "15.5")
        BigDecimal result
) {}