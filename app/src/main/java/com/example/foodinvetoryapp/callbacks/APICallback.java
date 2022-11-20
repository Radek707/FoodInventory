package com.example.foodinvetoryapp.callbacks;

public interface APICallback<T> {
    void onSuccess(T openFoodFactsResponse);
    void onFailure(String message);
}
