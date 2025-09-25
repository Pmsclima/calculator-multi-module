package com.wit.calculator.rest.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Calculator properties.
 *
 * @param basePath the base path of the API (e.g., /api/v1/calculator)
 * @param endpoints the endpoints of the API (e.g., /sum)
 */
@ConfigurationProperties(prefix = "api")
public record CalculatorProperties(
        String basePath,
        Endpoints endpoints
) {
    public record Endpoints(
            String sum
    ) {}
}