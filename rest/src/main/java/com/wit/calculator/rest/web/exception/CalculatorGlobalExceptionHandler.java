package com.wit.calculator.rest.web.exception;

import com.wit.calculator.rest.dto.CalculatorErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Calculator global exception handler.
 * Capture all exceptions, log them and return a generic error response.
 */
@Slf4j
@RestControllerAdvice
public class CalculatorGlobalExceptionHandler {
    /**
     * Bean Validation errors on the request body (HTTP 400).
     * 
     * @param methodArgumentNotValidException the exception thrown.
     * @param httpServletRequest the current request.
     *                           
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CalculatorErrorResponse> handleBodyValidation(
            final MethodArgumentNotValidException methodArgumentNotValidException,
            final HttpServletRequest httpServletRequest
    ) {
        final List<CalculatorErrorResponse.FieldViolation> violations = 
                methodArgumentNotValidException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::toViolation)
                .collect(Collectors.toList());

        log.warn("400 Validation Error at {} -> {}",
                httpServletRequest.getRequestURI(),
                violations
        );

        return build(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                "Request body validation failed",
                httpServletRequest.getRequestURI(),
                violations
        );
    }

    /**
     * Bean Validation errors on the request parameters (HTTP 400).
     * 
     * @param constraintViolationException the exception thrown.
     * @param httpServletRequest the current request.
     *                           
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CalculatorErrorResponse> handleConstraintViolation(
            final ConstraintViolationException constraintViolationException,
            final HttpServletRequest httpServletRequest
    ) {
       final List<CalculatorErrorResponse.FieldViolation> violations =
               constraintViolationException.getConstraintViolations()
                .stream()
                .map(this::toViolation)
                .collect(Collectors.toList());

        log.warn("400 Constraint Violation at {} -> {}",
                httpServletRequest.getRequestURI(),
                violations
        );

        return build(
                HttpStatus.BAD_REQUEST,
                "Validation Error",
                "Request parameter validation failed",
                httpServletRequest.getRequestURI(),
                violations
        );
    }

    /**
     * Malformed JSON (HTTP 400).
     *
     * @param httpMessageNotReadableException the exception thrown.
     * @param httpServletRequest the current request.
     *
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CalculatorErrorResponse> handleUnreadable(
            final HttpMessageNotReadableException httpMessageNotReadableException, 
            final HttpServletRequest httpServletRequest
    ) {
        httpMessageNotReadableException.getMostSpecificCause();
        final String detail = httpMessageNotReadableException.getMostSpecificCause().getMessage();

        log.warn("400 Malformed JSON at {} -> {}",
                httpServletRequest.getRequestURI(),
                detail
        );

        return build(
                HttpStatus.BAD_REQUEST,
                "Malformed JSON",
                "Request body is invalid or has wrong types",
                httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /**
     * Invalid request parameter type (HTTP 400).
     * 
     * @param methodArgumentTypeMismatchException the exception thrown.
     * @param httpServletRequest the current request.
     *                           
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<CalculatorErrorResponse> handleTypeMismatch(
            final MethodArgumentTypeMismatchException methodArgumentTypeMismatchException,
            final HttpServletRequest httpServletRequest
    ) {
       final String message = "Parameter '%s' has invalid value '%s'".formatted(
               methodArgumentTypeMismatchException.getName(), 
               methodArgumentTypeMismatchException.getValue()
       );

        log.warn("400 Type Mismatch at {} -> {}",
                httpServletRequest.getRequestURI(),
                message
        );

        return build(
                HttpStatus.BAD_REQUEST,
                "Type Mismatch",
                message, httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /**
     * Missing request parameter (HTTP 400).
     *
     * @param missingServletRequestParameterException the exception thrown.
     * @param httpServletRequest the current request.
     *
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<CalculatorErrorResponse> handleMissingParam(
           final MissingServletRequestParameterException missingServletRequestParameterException,
           final HttpServletRequest httpServletRequest
    ) {
        final String message = "Missing required parameter '%s'".formatted(
                missingServletRequestParameterException.getParameterName()
        );

        log.warn("400 Missing Parameter at {} -> {}",
                httpServletRequest.getRequestURI(),
                message
        );

        return build(
                HttpStatus.BAD_REQUEST,
                "Missing Parameter",
                message,
                httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /**
     * Illegal argument (HTTP 400).
     *
     * @param illegalArgumentException the exception thrown.
     * @param httpServletRequest the current request.
     *
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CalculatorErrorResponse> handleIllegalArgument(
           final IllegalArgumentException illegalArgumentException,
           final HttpServletRequest httpServletRequest
    ) {
        log.warn("400 Bad Request at {} -> {}",
                httpServletRequest.getRequestURI(),
                illegalArgumentException.getMessage()
        );

        return build(
                HttpStatus.BAD_REQUEST,
                "Bad Request",
                illegalArgumentException.getMessage(),
                httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /**
     * Endpoint Not found (HTTP 404).
     *
     * @param noHandlerFoundException the exception thrown.
     * @param httpServletRequest the current request.
     *
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<CalculatorErrorResponse> handleNotFound(
            final NoHandlerFoundException noHandlerFoundException,
            final HttpServletRequest httpServletRequest
    ) {
        log.warn("404 Not Found at {} -> {}",
                httpServletRequest.getRequestURI(),
                noHandlerFoundException.getRequestURL()
        );

        return build(
                HttpStatus.NOT_FOUND,
                "Not Found",
                "Endpoint not found",
                httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /**
     * Method not allowed (HTTP 405).
     *
     * @param httpRequestMethodNotSupportedException the exception thrown.
     * @param httpServletRequest the current request.
     *
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<CalculatorErrorResponse> handleMethodNotSupported(
            final HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException,
            final HttpServletRequest httpServletRequest
    ) {
        final String message = "Method '%s' is not supported for this request";

        log.warn("405 Method not allowed at {} -> {}",
                httpServletRequest.getRequestURI(),
                message
        );

        return build(
                HttpStatus.METHOD_NOT_ALLOWED,
                "Method Not Allowed",
                message,
                httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /**
     * Unsupported media type (HTTP 415).
     *
     * @param httpMediaTypeNotSupportedException the exception thrown.
     * @param httpServletRequest the current request.
     *
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<CalculatorErrorResponse> handleUnsupportedMedia(
           final HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException,
           final HttpServletRequest httpServletRequest
    ) {
        log.warn("415 Unsupported Media Type at {} -> {}",
                httpServletRequest.getRequestURI(),
                httpMediaTypeNotSupportedException.getMessage()
        );

        return build(
                HttpStatus.UNSUPPORTED_MEDIA_TYPE,
                "Unsupported Media Type",
                "Content-Type not supported",
                httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /**
     * Internal server error (HTTP 500).
     *
     * @param exception the exception thrown.
     * @param httpServletRequest the current request.
     *
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<CalculatorErrorResponse> handleGeneric(
            final Exception exception,
            final HttpServletRequest httpServletRequest
    ) {
        log.error("500 Unexpected error at {} -> {}",
                httpServletRequest.getRequestURI(),
                exception.getMessage(),
                exception
        );

        return build(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Internal Server Error",
                "Unexpected error",
                httpServletRequest.getRequestURI(),
                List.of()
        );
    }

    /* ===================== helpers ===================== */

    /**
     * Converts a {@link FieldError} to a {@link CalculatorErrorResponse.FieldViolation}.
     *
     * @param fieldError the error to convert.
     *
     * @return the converted error.
     */
    private CalculatorErrorResponse.FieldViolation toViolation(FieldError fieldError) {
        return new CalculatorErrorResponse.FieldViolation(fieldError.getField(), fieldError.getDefaultMessage());
    }

    /**
     * Converts a {@link ConstraintViolation} to a {@link CalculatorErrorResponse.FieldViolation}.
     * @param constraintViolation the error to convert.
     * @return the converted error.
     */
    private CalculatorErrorResponse.FieldViolation toViolation(ConstraintViolation<?> constraintViolation) {
        final String path = constraintViolation.getPropertyPath() == null ? "" :
                constraintViolation.getPropertyPath().toString();
        final String field = path.contains(".") ? path.substring(path.lastIndexOf('.') + 1) : path;

        return new CalculatorErrorResponse.FieldViolation(field, constraintViolation.getMessage());
    }

    /**
     * Builds a consistent error payload and wraps it in a {@link ResponseEntity}.
     *
     * @param status     HTTP status to return.
     * @param error      short error title.
     * @param message    detailed error message.
     * @param path       request URI.
     * @param violations list of field violations (empty if not applicable).
     *                   
     * @return a ResponseEntity containing {@link CalculatorErrorResponse}.
     */
    private ResponseEntity<CalculatorErrorResponse> build(
            final HttpStatus status,
            final String error,
            final String message,
            final String path,
            final List<CalculatorErrorResponse.FieldViolation> violations
    ) {
        final CalculatorErrorResponse calculatorErrorResponse  = new CalculatorErrorResponse(
                Instant.now(),
                status.value(),
                error,
                message,
                path,
                violations.isEmpty() ? null : violations
        );
        return new ResponseEntity<>(calculatorErrorResponse, status);
    }
}