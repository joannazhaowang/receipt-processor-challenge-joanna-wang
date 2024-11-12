package com.example.receiptprocessorchallengejoannawang.controller;

import com.example.receiptprocessorchallengejoannawang.model.Item;
import com.example.receiptprocessorchallengejoannawang.model.Receipt;
import com.example.receiptprocessorchallengejoannawang.service.ReceiptService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptProcessController.class)
class ReceiptProcessControllerTest {

    private static final String TEST_ID = "331feb42-5d85-4f52-a21b-b3c5995568c4";

    private static final String INVALID_RECEIPT_MESSAGE = "The receipt is invalid";

    private static final Item TEST_ITEM_1 = new Item("Apple", "1.00");
    private static final Item TEST_ITEM_2 = new Item("Pear", "1.50");
    private static final Item TEST_ITEM_3 = new Item("Banana", "2.00");

    private static final Receipt VALID_TEST_RECEIPT = new Receipt(
            "Target",
            LocalDate.of(2024, 11, 4),
            LocalTime.of(15, 5),
            List.of(TEST_ITEM_1, TEST_ITEM_2, TEST_ITEM_3),
            "36.00");

    private static final Receipt INVALID_TEST_RECEIPT = new Receipt(
            "Target",
            LocalDate.of(2024, 11, 4),
            LocalTime.of(15, 5),
            List.of(),
            "36.00");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testProcessValidReceiptSuccess() throws Exception {
        Mockito.when(receiptService.processReceipt(any(Receipt.class))).thenReturn(TEST_ID);

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(VALID_TEST_RECEIPT)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(TEST_ID));
    }

    @Test
    void testProcessInvalidReceiptFail() throws Exception {
        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(INVALID_TEST_RECEIPT)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value(INVALID_RECEIPT_MESSAGE));
    }
}
