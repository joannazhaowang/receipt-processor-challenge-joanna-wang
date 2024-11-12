package com.example.receiptprocessorchallengejoannawang.controller;

import com.example.receiptprocessorchallengejoannawang.model.Receipt;
import com.example.receiptprocessorchallengejoannawang.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/receipts")
@Validated
public class ReceiptProcessController {

    private final ReceiptService receiptService;

    public ReceiptProcessController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Object> processReceipt(@Valid @RequestBody Receipt receipt) {
        String receiptId = receiptService.processReceipt(receipt);
        Map<String, String> response = new HashMap<>();
        response.put("id", receiptId);
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        // Return 400 if the http message is invalid
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("description", "The http message is invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        // Return 400 if the http message is not valid receipt object
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("description", "The receipt is invalid");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
