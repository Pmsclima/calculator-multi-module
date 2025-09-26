package com.wit.calculator.rest.web;

import com.wit.calculator.rest.dto.CalculatorRequest;
import com.wit.calculator.rest.dto.CalculatorResponse;
import com.wit.calculator.rest.dto.CalculatorErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Calculator API for swagger documentation purposes.
 * Implementation is in {@link CalculatorController}.
 */
@Tag(name = "Calculator", description = "Basic API calculator operations.")
public interface CalculatorApi {
    @Operation(
            summary = "Add two numbers.",
            description = "Adds two numbers."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = CalculatorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse .class))
            ),
    })
    ResponseEntity<CalculatorResponse> sum(@Valid @RequestBody final CalculatorRequest calculatorRequest);

    @Operation(
            summary = "Subtract two numbers.",
            description = "Subtract two numbers."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = CalculatorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse .class))
            ),
    })
    ResponseEntity<CalculatorResponse> sub(@Valid @RequestBody final CalculatorRequest calculatorRequest);

    @Operation(
            summary = "Multiply two numbers.",
            description = "Multiply two numbers."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = CalculatorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse .class))
            ),
    })
    ResponseEntity<CalculatorResponse> multiply(@Valid @RequestBody final CalculatorRequest calculatorRequest);

    @Operation(
            summary = "Divide two numbers.",
            description = "Divide two numbers."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = CalculatorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = CalculatorErrorResponse .class))
            ),
    })
    ResponseEntity<CalculatorResponse> division(@Valid @RequestBody final CalculatorRequest calculatorRequest);
}