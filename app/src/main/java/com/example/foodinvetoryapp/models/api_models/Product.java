package com.example.foodinvetoryapp.models.api_models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Product {
    @JsonProperty("product_name")
    private String foodProductName;
    @JsonProperty("image_front_small_url")
    private String frontImageUrl;
    @JsonProperty("nutriscore_grade")
    private String nutrientScore;

    public String getFrontImageUrl() {
        return frontImageUrl;
    }

    public String getNutrientScore() {
        return nutrientScore;
    }

    public String getFoodProductName() {
        return foodProductName;
    }
}
