package com.example.receiptprocessorchallengejoannawang.controller;

import com.example.receiptprocessorchallengejoannawang.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReceiptRetrieveController.class)
class ReceiptRetrieveControllerTest {

    private static final String VALID_ID = "331feb42-5d85-4f52-a21b-b3c5995568c4";
    private static final int VALID_POINTS = 100;

    private static final String NON_EXISTING_ID = "non-existing-id";
    private static final String NON_EXISTING_MESSAGE = "No receipt found for that id";

    private static final String INVALID_ID_MESSAGE = "The ID is invalid";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptService receiptService;

    @Test
    void testGetPointsForValidIdSuccess() throws Exception {
        Mockito.when(receiptService.getPoints(VALID_ID)).thenReturn(Optional.of(VALID_POINTS));

        mockMvc.perform(get("/receipts/" + VALID_ID + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").value(VALID_POINTS));
    }

    @Test
    void testGetPointsForNonExistingIdFail() throws Exception {
        Mockito.when(receiptService.getPoints(NON_EXISTING_ID)).thenReturn(Optional.empty());

        mockMvc.perform(get("/receipts/" + NON_EXISTING_ID + "/points"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.description").value(NON_EXISTING_MESSAGE));
    }

    @Test
    void testGetPointsForInvalidIdFail() throws Exception {
        mockMvc.perform(get("/receipts/ /points"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value(INVALID_ID_MESSAGE));
    }
}
