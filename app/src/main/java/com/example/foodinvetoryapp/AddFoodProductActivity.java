package com.example.foodinvetoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.repository.MyRepository;
import com.example.foodinvetoryapp.repository.RepositoryProvider;

public class AddFoodProductActivity extends AppCompatActivity {

    public static final int NO_FOOD_PRODUCT = -1;
    private FoodProduct foodProduct;
    private MyRepository myRepository;
    private EditText addFoodProductNameEditText;
    private Storage currentStorage;
    private long foodProductId;
    private long storageId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_product);

        addFoodProductNameEditText = findViewById(R.id.addFoodProductNameEditText);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> saveFoodProduct());

        Button deleteButton = findViewById(R.id.deleteButton);

        myRepository = RepositoryProvider.getInstance(this);

        Intent intent = getIntent();
        storageId = intent.getLongExtra(TAG.STORAGE_ID, -1);
        currentStorage = myRepository.getStorageById(storageId);
        foodProductId = intent.getLongExtra(TAG.FOOD_PRODUCT_ID, NO_FOOD_PRODUCT);

        if (foodProductId != NO_FOOD_PRODUCT) {
            deleteButton.setOnClickListener(view -> deleteFoodProduct());
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
            updateFoodProductUI(foodProduct);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void deleteFoodProduct() {
    }

    private void updateFoodProductUI(FoodProduct foodProduct) {

    }

    private void saveFoodProduct() {
        String foodProductName = addFoodProductNameEditText.getText().toString();

        if (foodProduct == null) {
            FoodProduct foodProduct = new FoodProduct();
            foodProduct.setStorageId(currentStorage.getId());
            foodProduct.setName(foodProductName);
            myRepository.addFoodProduct(foodProduct);
        } else {
            foodProduct.setName(foodProductName);
            myRepository.editFoodProduct(foodProduct);
        }

        Toast.makeText(this, "Food product saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}