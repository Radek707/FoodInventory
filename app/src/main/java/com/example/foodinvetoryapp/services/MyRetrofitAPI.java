package com.example.foodinvetoryapp.services;


import com.example.foodinvetoryapp.TAG;
import com.example.foodinvetoryapp.callbacks.APICallback;
import com.example.foodinvetoryapp.models.api_models.OpenFoodFactsResponse;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MyRetrofitAPI {
    public static MyRetrofitAPI instance;

    private Retrofit retrofit;
    private static final ObjectMapper objectMapper =
            new ObjectMapper()
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private MyRetrofitAPI() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(TAG.BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();
    }

    public static MyRetrofitAPI getInstance() {
        if (instance == null) {
            return new MyRetrofitAPI();
        }
        return instance;
    }

    public void getOpenFoodFactsResponse(APICallback<OpenFoodFactsResponse> callback, String barcode) {
        OpenFoodFactsService service = retrofit.create(OpenFoodFactsService.class);
        Call<OpenFoodFactsResponse> call = service.searchProductById(barcode);
        call.enqueue(new Callback<OpenFoodFactsResponse>() {
            @Override
            public void onResponse(Call<OpenFoodFactsResponse> call, Response<OpenFoodFactsResponse> response) {
                if (response.isSuccessful()) {
                    OpenFoodFactsResponse body = response.body();
                    callback.onSuccess(body);
                } else {
                    callback.onFailure("Fail to respond, error: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<OpenFoodFactsResponse> call, Throwable t) {
                callback.onFailure("Connection failed.");
                t.printStackTrace();
            }
        });
    }
}
