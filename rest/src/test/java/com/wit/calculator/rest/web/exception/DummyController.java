package com.wit.calculator.rest.web.exception;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/test")
public class DummyController {
    @PostMapping(
            value = "/body",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> body(@Valid @RequestBody Dummy req) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/params")
    public ResponseEntity<Void> params(@RequestParam @Min(1) Integer n) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/boom")
    public ResponseEntity<Void> boom() {
        throw new RuntimeException("boom");
    }

    public record Dummy(
            @NotNull
            String firstNumber,
            @NotNull String secondNumber
    ) {}
}