package com.example.receiptprocessorchallengejoannawang.service;

import com.example.receiptprocessorchallengejoannawang.model.Item;
import com.example.receiptprocessorchallengejoannawang.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReceiptServiceTest {

    private static final String NON_EXISTING_ID = "non-existing-id";

    private static final Item TEST_ITEM_1 = new Item("Apple", "1.00");
    private static final Item TEST_ITEM_2 = new Item("Pear", "1.50");
    private static final Item TEST_ITEM_3 = new Item("Banana", "2.00");

    private static final Receipt TEST_RECEIPT_1 = new Receipt(
            "Target",
            LocalDate.of(2024, 11, 4),
            LocalTime.of(15, 5),
            List.of(TEST_ITEM_1, TEST_ITEM_2),
            "36.00");
    private static final int TEST_POINTS_1 = 96;

    private static final Receipt TEST_RECEIPT_2 = new Receipt(
            "Target",
            LocalDate.of(2024, 11, 4),
            LocalTime.of(8, 5),
            List.of(TEST_ITEM_1, TEST_ITEM_3),
            "36.30");
    private static final int TEST_POINTS_2 = 12;

    @InjectMocks
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessReceipt() {
        String receiptId1 = receiptService.processReceipt(TEST_RECEIPT_1);

        assertTrue(receiptId1 != null && !receiptId1.isEmpty(), "Receipt ID should not be null or empty");

        Optional<Integer> points1 = receiptService.getPoints(receiptId1);
        assertTrue(points1.isPresent() && points1.get() == TEST_POINTS_1, "Points is not equal to expected");

        String receiptId2 = receiptService.processReceipt(TEST_RECEIPT_2);

        assertTrue(receiptId2 != null && !receiptId2.isEmpty(), "Receipt ID should not be null or empty");

        Optional<Integer> points2 = receiptService.getPoints(receiptId2);
        assertTrue(points2.isPresent() && points2.get() == TEST_POINTS_2, "Points is not equal to expected");
    }

    @Test
    void testGetPointsForExistingReceipt() {
        String receiptId = receiptService.processReceipt(TEST_RECEIPT_1);

        Optional<Integer> points = receiptService.getPoints(receiptId);
        assertTrue(points.isPresent() && points.get() == TEST_POINTS_1, "Points is not equal to expected");
    }

    @Test
    void testGetPointsForNonExistingReceipt() {
        Optional<Integer> points = receiptService.getPoints(NON_EXISTING_ID);
        assertTrue(points.isEmpty(), "Points should not be present for invalid ID");
    }
}
