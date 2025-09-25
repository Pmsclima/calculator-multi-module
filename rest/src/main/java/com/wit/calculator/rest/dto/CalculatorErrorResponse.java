package com.wit.calculator.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;
import java.util.List;

/**
 * Error response payload for calculator.
 *
 * @param timeStamp timestamp of the error.
 * @param statusCode the HTTP status code.
 * @param error the short error message.
 * @param message the detailed error message.
 * @param path the path of the request.
 * @param violationList the list of field violations.
 */
@Schema(description = "Error response payload for calculator.")
public record CalculatorErrorResponse(
        @Schema(description = "The timestamp of the error.", example = "2022-01-01T00:00:00Z")
        Instant timeStamp,
        @Schema(description = "The HTTP status code.", example = "400")
        Integer statusCode,
        @Schema(description = "Short error message.", example = "Bad Request")
        String error,
        @Schema(description = "Detailed error message.", example = "Request body validation fail")
        String message,
        @Schema(description = "The path of the request.", example = "/api/v1/calculator/sum")
        String path,
        @Schema(description = "The list of field violations.")
        List<FieldViolation> violationList
) {
    /**
     * Field violation.
     *
     * @param fieldName the field name.
     * @param message the validation message.
     */
    public record FieldViolation(
            @Schema(description = "The field name.", example = "firstNumber")
            String fieldName,
            @Schema(description = "The validation message.", example = "must not null.")
            String message
    ) {}
}