package com.example.foodinvetoryapp.models.api_models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("product_name")
    private String foodProductName;

    public String getFoodProductName() {
        return foodProductName;
    }
}
