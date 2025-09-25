package service;

import com.wit.calculator.domain.CalculatorBinaryOperands;
import com.wit.calculator.service.CalculatorServiceImpl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorServiceTest {
    private final CalculatorServiceImpl service = new CalculatorServiceImpl();

    @Nested
    @DisplayName("SUM")
    class Sum {
        @Test
        void sum_addsTwoPositiveDecimals() {
            var ops = new CalculatorBinaryOperands(new BigDecimal("10.5"), new BigDecimal("10.5"));
            var result = service.sum(ops);
            assertEquals(new BigDecimal("21.0"), result);
        }

        @Test
        void sum_handlesNegatives() {
            var ops = new CalculatorBinaryOperands(new BigDecimal("-1.2"), new BigDecimal("3.2"));
            var result = service.sum(ops);
            assertEquals(new BigDecimal("2.0"), result);
        }

        @Test
        void sum_nullOperandThrows() {
            var ops = new CalculatorBinaryOperands(null, new BigDecimal("2"));
            assertThrows(IllegalArgumentException.class, () -> service.sum(ops));
        }
    }
}