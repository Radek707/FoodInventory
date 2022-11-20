package com.example.foodinvetoryapp.services;

import com.example.foodinvetoryapp.models.api_models.OpenFoodFactsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface OpenFoodFactsService {
    @GET("{barcode}.json")
    Call<OpenFoodFactsResponse> searchProductById (@Path("barcode") String barcode);
}
