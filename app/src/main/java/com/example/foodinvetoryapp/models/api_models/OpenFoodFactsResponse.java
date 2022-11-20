package com.example.foodinvetoryapp.models.api_models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenFoodFactsResponse {
    @JsonProperty("code")
    private String code;

    @JsonProperty("product")
    private Product product;

    public String getCode() {
        return code;
    }

    public Product getProduct() {
        return product;
    }
}
