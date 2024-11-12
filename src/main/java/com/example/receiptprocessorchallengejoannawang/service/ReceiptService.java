package com.example.receiptprocessorchallengejoannawang.service;

import com.example.receiptprocessorchallengejoannawang.model.Item;
import com.example.receiptprocessorchallengejoannawang.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReceiptService {

    private final Map<String, Integer> receiptPoints = new HashMap<>();

    public String processReceipt(Receipt receipt) {
        String receiptId = UUID.randomUUID().toString();
        int points = calculatePoints(receipt);
        receiptPoints.put(receiptId, points);
        return receiptId;
    }

    public Optional<Integer> getPoints(String receiptId) {
        return Optional.ofNullable(receiptPoints.getOrDefault(receiptId, null));
    }

    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // 1. One point for every alphanumeric character in the retailer name
        points += countAlphanumericCharacters(receipt.getRetailer());

        // 2. 50 points if the total is a round dollar amount with no cents
        if (isRoundDollarAmount(receipt.getTotal())) {
            points += 50;
        }

        // 3. 25 points if the total is a multiple of 0.25
        if (isMultipleOfQuarter(receipt.getTotal())) {
            points += 25;
        }

        // 4. 5 points for every two items on the receipt
        points += (receipt.getItems().size() / 2) * 5;

        // 5. Points for item descriptions with length multiple of 3
        points += calculateItemDescriptionPoints(receipt);

        // 6. 6 points if the day in the purchase date is odd
        if (isDayOdd(receipt.getPurchaseDate().getDayOfMonth())) {
            points += 6;
        }

        // 7. 10 points if the time of purchase is after 2:00pm and before 4:00pm
        if (isBetween2pmAnd4pm(receipt.getPurchaseTime())) {
            points += 10;
        }

        return points;
    }

    private int countAlphanumericCharacters(String retailer) {
        return (int) retailer.chars()
                .filter(Character::isLetterOrDigit)
                .count();
    }

    private boolean isRoundDollarAmount(String total) {
        BigDecimal totalAmount = new BigDecimal(total);
        return totalAmount.stripTrailingZeros().scale() <= 0;
    }

    private boolean isMultipleOfQuarter(String total) {
        BigDecimal totalAmount = new BigDecimal(total);
        BigDecimal quarter = new BigDecimal("0.25");
        return totalAmount.remainder(quarter).compareTo(BigDecimal.ZERO) == 0;
    }

    private int calculateItemDescriptionPoints(Receipt receipt) {
        int points = 0;
        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                BigDecimal price = new BigDecimal(item.getPrice());
                int descriptionPoints = price.multiply(BigDecimal.valueOf(0.2))
                        .setScale(0, RoundingMode.UP)
                        .intValue();
                points += descriptionPoints;
            }
        }
        return points;
    }

    private boolean isDayOdd(int dayOfMonth) {
        return dayOfMonth % 2 != 0;
    }

    private boolean isBetween2pmAnd4pm(LocalTime purchaseTime) {
        LocalTime start = LocalTime.of(14, 0);
        LocalTime end = LocalTime.of(16, 0);
        return purchaseTime.isAfter(start) && purchaseTime.isBefore(end);
    }
}
