package com.example.receiptprocessorchallengejoannawang.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Receipt {

    @JsonProperty("retailer")
    @NotEmpty
    @Pattern(regexp = "^[\\w\\s\\-&]+$")
    private String retailer;

    @JsonProperty("purchaseDate")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate purchaseDate;

    @JsonProperty("purchaseTime")
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime purchaseTime;

    @JsonProperty("items")
    @NotNull
    @Size(min = 1)
    private List<@Valid Item> items;

    @JsonProperty("total")
    @NotEmpty
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String total;

    public Receipt() {}

    public Receipt(String retailer, LocalDate purchaseDate, LocalTime purchaseTime, List<@Valid Item> items, String total) {
        this.retailer = retailer;
        this.purchaseDate = purchaseDate;
        this.purchaseTime = purchaseTime;
        this.items = items;
        this.total = total;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalTime getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(LocalTime purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}

