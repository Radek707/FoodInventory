package com.example.foodinvetoryapp.repository;

import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;

import java.util.List;

public interface MyRepository {
    void addFoodProduct(FoodProduct foodProduct);
    void editFoodProduct(FoodProduct foodProduct);
    void deleteFoodProduct(long id);
    FoodProduct getFoodProductById(long id);
    List<FoodProduct> getAllFoodProducts();

    void addStorage(Storage storage);
    void editStorage(Storage storage);
    void detleteStorage(Storage storage);
    List<Storage> getAllStorages();
}
