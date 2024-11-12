package com.example.receiptprocessorchallengejoannawang.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Item {

    @JsonProperty("shortDescription")
    @NotEmpty
    @Pattern(regexp = "^[\\w\\s\\-]+$")
    private String shortDescription;

    @JsonProperty("price")
    @NotEmpty
    @Pattern(regexp = "^\\d+\\.\\d{2}$")
    private String price;

    public Item() {}

    public Item(String shortDescription, String price) {
        this.shortDescription = shortDescription;
        this.price = price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
