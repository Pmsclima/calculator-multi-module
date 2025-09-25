package com.wit.calculator.service;

import com.wit.calculator.domain.CalculatorBinaryOperands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Calculator core service implementation.
 */
@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {
    private static final MathContext mathContext = MathContext.DECIMAL128;
    private static final String VALIDATION_NOT_NULL = "{validation.notNull}";
    private static final Supplier<IllegalArgumentException> NOT_NULL =
            () -> new IllegalArgumentException(VALIDATION_NOT_NULL);


    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal sum(final CalculatorBinaryOperands calculatorBinaryOperands) {
        validateOperands(calculatorBinaryOperands);

        log.info("calculate sum of {} and {}",
                calculatorBinaryOperands.firstNumber(),
                calculatorBinaryOperands.secondNumber()
        );

        return calculatorBinaryOperands.firstNumber().add(calculatorBinaryOperands.secondNumber(), mathContext);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public BigDecimal sub(CalculatorBinaryOperands calculatorBinaryOperands) {
        validateOperands(calculatorBinaryOperands);

        log.info("calculate subtraction of {} and {}",
                calculatorBinaryOperands.firstNumber(),
                calculatorBinaryOperands.secondNumber()
        );

        return calculatorBinaryOperands.firstNumber().subtract(calculatorBinaryOperands.secondNumber(), mathContext);
    }

    /**
     * Validates the value of parameters.
     *
     * @param calculatorBinaryOperands containing the values.
     */
    private void validateOperands(final CalculatorBinaryOperands calculatorBinaryOperands) {
        Optional.ofNullable(calculatorBinaryOperands).orElseThrow(NOT_NULL);
        Optional.ofNullable(calculatorBinaryOperands.firstNumber()).orElseThrow(NOT_NULL);
        Optional.ofNullable(calculatorBinaryOperands.secondNumber()).orElseThrow(NOT_NULL);
    }
}