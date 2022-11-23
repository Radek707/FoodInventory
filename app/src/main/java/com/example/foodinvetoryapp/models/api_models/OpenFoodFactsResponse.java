package com.example.foodinvetoryapp.models.api_models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OpenFoodFactsResponse {
    @JsonProperty("code")
    private String code;
    @JsonProperty("product")
    private Product product;
    @JsonProperty("status_verbose")
    private String status;

    public String getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public Product getProduct() {
        return product;
    }
}
