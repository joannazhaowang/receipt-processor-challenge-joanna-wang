package com.example.receiptprocessorchallengejoannawang.controller;

import com.example.receiptprocessorchallengejoannawang.service.ReceiptService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/receipts")
@Validated
public class ReceiptRetrieveController {

    private final ReceiptService receiptService;

    public ReceiptRetrieveController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Object> getPoints(@PathVariable @Pattern(regexp = "^\\S+$") String id) {
        Optional<Integer> points = receiptService.getPoints(id);
        if (points.isPresent()) {
            Map<String, Integer> response = new HashMap<>();
            response.put("points", points.get());
            return ResponseEntity.ok(response);
        } else {
            // Return 404 Not Found if no receipt found for given ID
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("description", "No receipt found for that id");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Return 400 if the http message is invalid
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("description", "The http message is invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        // Return 400 if the http message is not valid ID
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("description", "The ID is invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
