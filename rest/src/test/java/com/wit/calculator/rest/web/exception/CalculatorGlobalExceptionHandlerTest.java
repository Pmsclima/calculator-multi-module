package com.wit.calculator.rest.web.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DummyController.class)
@Import(CalculatorGlobalExceptionHandler.class)
 class CalculatorGlobalExceptionHandlerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    void whenBodyValidationFails_returns400() throws Exception {
        var req = new DummyController.Dummy(null, "2");
        mockMvc.perform(post("/test/body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.violationList[0].fieldName").value("firstNumber"));
    }

    @Test
    void whenMalformedJson_returns400() throws Exception {
        mockMvc.perform(post("/test/body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ bad json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Malformed JSON"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void whenUnsupportedMedia_returns415() throws Exception {
        mockMvc.perform(post("/test/body")
                        .contentType(MediaType.TEXT_PLAIN)
                        .content("whatever"))
                .andExpect(status().isUnsupportedMediaType())
                .andExpect(jsonPath("$.error").value("Unsupported Media Type"))
                .andExpect(jsonPath("$.statusCode").value(415));
    }

    @Test
    void whenConstraintViolationParam_returns400() throws Exception {
        mockMvc.perform(get("/test/params")
                        .param("n", "0"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Error"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void whenTypeMismatch_returns400() throws Exception {
        mockMvc.perform(get("/test/params")
                        .param("n", "abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Type Mismatch"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void whenMissingParam_returns400() throws Exception {
        mockMvc.perform(get("/test/params"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Missing Parameter"))
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    void whenGenericException_returns500() throws Exception {
        mockMvc. perform(post("/test/boom"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Internal Server Error"))
                .andExpect(jsonPath("$.statusCode").value(500));
    }
}