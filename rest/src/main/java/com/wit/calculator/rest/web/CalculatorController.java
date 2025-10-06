package com.wit.calculator.rest.web;

import com.wit.calculator.domain.CalculatorBinaryOperands;
import com.wit.calculator.kafka.CalculationEvent;
import com.wit.calculator.rest.kafka.CalculatorProducer;
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
    private final CalculatorProducer calculatorProducer;

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

        calculatorProducer.send(new CalculationEvent(
                "SUM",
                calculatorRequest.firstNumber().toPlainString(),
                calculatorRequest.secondNumber().toPlainString(),
                result.toPlainString()
        ));

        log.info("REST sum result: {}", result);
        return new ResponseEntity<>(new CalculatorResponse(result), HttpStatus.OK);
    }

    /**
     * Subtracts two operands.
     *
     * @param calculatorRequest the payload body containing operands firstNumber and secondNumber.
     *
     * @return the result of the subtract operation.
     */
    @Override
    @PostMapping(
            path = "${api.endpoints.sub}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalculatorResponse> sub(CalculatorRequest calculatorRequest) {
        log.info("REST sub of {} and {}", calculatorRequest.firstNumber(), calculatorRequest.secondNumber());

        final BigDecimal result = calculatorService.sub(
                new CalculatorBinaryOperands(
                        calculatorRequest.firstNumber(),
                        calculatorRequest.secondNumber()
                ));

        calculatorProducer.send(new CalculationEvent(
                "SUB",
                calculatorRequest.firstNumber().toPlainString(),
                calculatorRequest.secondNumber().toPlainString(),
                result.toPlainString()
        ));

        log.info("REST sub result: {}", result);
        return new ResponseEntity<>(new CalculatorResponse(result), HttpStatus.OK);
    }

    /**
     * Multiply two operands.
     *
     * @param calculatorRequest the payload body containing operands firstNumber and secondNumber.
     *
     * @return the result of the multiplication operation.
     */
    @Override
    @PostMapping(
            path = "${api.endpoints.mult}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalculatorResponse> multiply(CalculatorRequest calculatorRequest) {
        log.info("REST mult of {} and {}", calculatorRequest.firstNumber(), calculatorRequest.secondNumber());

        final BigDecimal result = calculatorService.mult(
                new CalculatorBinaryOperands(
                        calculatorRequest.firstNumber(),
                        calculatorRequest.secondNumber()
                ));

        calculatorProducer.send(new CalculationEvent(
                "MULT",
                calculatorRequest.firstNumber().toPlainString(),
                calculatorRequest.secondNumber().toPlainString(),
                result.toPlainString()
        ));

        log.info("REST mult result: {}", result);
        return new ResponseEntity<>(new CalculatorResponse(result), HttpStatus.OK);
    }

    /**
     * Divide two operands.
     *
     * @param calculatorRequest the payload body containing operands firstNumber and secondNumber.
     *
     * @return the result of the division operation.
     */
    @Override
    @PostMapping(
            path = "${api.endpoints.div}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CalculatorResponse> division(CalculatorRequest calculatorRequest) {
        log.info("REST div of {} and {}", calculatorRequest.firstNumber(), calculatorRequest.secondNumber());

        final BigDecimal result = calculatorService.division(
                new CalculatorBinaryOperands(
                        calculatorRequest.firstNumber(),
                        calculatorRequest.secondNumber()
                ));

        calculatorProducer.send(new CalculationEvent(
                "DIV",
                calculatorRequest.firstNumber().toPlainString(),
                calculatorRequest.secondNumber().toPlainString(),
                result.toPlainString()
        ));

        log.info("REST div result: {}", result);
        return new ResponseEntity<>(new CalculatorResponse(result), HttpStatus.OK);
    }
}