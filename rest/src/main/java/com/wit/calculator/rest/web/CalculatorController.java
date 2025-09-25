package com.wit.calculator.rest.web;

import com.wit.calculator.domain.CalculatorBinaryOperands;
import com.wit.calculator.service.CalculatorService;
import com.wit.calculator.rest.dto.CalculatorRequest;
import com.wit.calculator.rest.dto.CalculatorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * Calculator REST controller implementing {@link CalculatorApi}.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}")
public class CalculatorController implements CalculatorApi {
    private final CalculatorService calculatorService;

    /**
     * Adds two operands and sum.
     *
     * @param calculatorRequest the payload body containing operands firstNumber and secondNumber.
     *
     * @return the result of the sum operation.
     */
    @Override
    @PostMapping(
            path = "${api.endpoints.sum}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalculatorResponse> sum(final CalculatorRequest calculatorRequest) {
        log.info("REST sum of {} and {}", calculatorRequest.firstNumber(), calculatorRequest.secondNumber());
        final BigDecimal result = calculatorService.sum(
                new CalculatorBinaryOperands(
                        calculatorRequest.firstNumber(),
                        calculatorRequest.secondNumber()
                ));

        log.info("REST sum result: {}", result);
        return new ResponseEntity<>(new CalculatorResponse(result), HttpStatus.OK);
    }
}