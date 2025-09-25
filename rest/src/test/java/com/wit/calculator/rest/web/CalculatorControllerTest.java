package com.wit.calculator.rest.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wit.calculator.domain.CalculatorBinaryOperands;
import com.wit.calculator.rest.dto.CalculatorRequest;
import com.wit.calculator.service.CalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CalculatorService calculatorService;

    private static final String SUM = "/api/v1/calculator/sum";
    private static final String SUB = "/api/v1/calculator/sub";

    @Nested
    @DisplayName("SUM")
    class Sum {
        @Test
        @DisplayName("POST /sum -> 200 OK")
        void sum_ok() throws Exception {
            var req = new CalculatorRequest(new BigDecimal("10.5"), new BigDecimal("10.5"));

            Mockito.when(calculatorService.sum(any(CalculatorBinaryOperands.class)))
                    .thenReturn(new BigDecimal("21.0"));

            mockMvc.perform(post(SUM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value("21.0"));
        }

        @Test
        @DisplayName("POST /sum -> 400 Bad Request (validation error)")
        void sum_badRequest() throws Exception {
            String body = """
                        {"secondNumber": 5}
                    """;

            mockMvc.perform(post(SUM).
                    contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                    .andExpect(status().isBadRequest()).
                            andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"));
        }

        @Test
        @DisplayName("POST /sum -> 500 Internal Server Error")
        void sum_internalError() throws Exception {
            var req = new CalculatorRequest(new BigDecimal("1"), new BigDecimal("2"));

            Mockito.when(calculatorService.sum(any(CalculatorBinaryOperands.class)))
                    .thenThrow(new RuntimeException("boom"));

            mockMvc.perform(post(SUM)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.statusCode").value(500))
                    .andExpect(jsonPath("$.error").value("Internal Server Error"));
        }
    }

    @Nested
    @DisplayName("SUB")
    class Sub {
        @Test
        @DisplayName("POST /sub -> 200 OK")
        void sub_ok() throws Exception {
            var req = new CalculatorRequest(new BigDecimal("12.5"), new BigDecimal("10"));

            Mockito.when(calculatorService.sub(any(CalculatorBinaryOperands.class)))
                    .thenReturn(new BigDecimal("21.0"));

            mockMvc.perform(post(SUB)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value("21.0"));
        }

        @Test
        @DisplayName("POST /sub -> 400 Bad Request (validation error)")
        void sub_badRequest() throws Exception {
            String body = """
                        {"secondNumber": 5}
                    """;

            mockMvc.perform(post(SUB)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.statusCode").value(400))
                    .andExpect(jsonPath("$.error").value("Validation Error"));
        }

        @Test
        @DisplayName("POST /sub -> 500 Internal Server Error")
        void sub_internalError() throws Exception {
            var req = new CalculatorRequest(new BigDecimal("1"), new BigDecimal("2"));

            Mockito.when(calculatorService.sub(any(CalculatorBinaryOperands.class)))
                    .thenThrow(new RuntimeException("boom"));

            mockMvc.perform(post(SUB)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(req)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(jsonPath("$.statusCode").value(500))
                    .andExpect(jsonPath("$.error").value("Internal Server Error"));
        }
    }
}