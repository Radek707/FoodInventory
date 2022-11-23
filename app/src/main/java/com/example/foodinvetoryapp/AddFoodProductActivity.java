package com.example.foodinvetoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodinvetoryapp.callbacks.APICallback;
import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.models.api_models.OpenFoodFactsResponse;
import com.example.foodinvetoryapp.repository.MyRepository;
import com.example.foodinvetoryapp.repository.RepositoryProvider;
import com.example.foodinvetoryapp.services.MyRetrofitAPI;

public class AddFoodProductActivity extends AppCompatActivity implements APICallback<OpenFoodFactsResponse> {

    public static final int NO_FOOD_PRODUCT = -1;
    private FoodProduct foodProduct;
    private MyRepository myRepository;
    private EditText addFoodProductNameEditText;
    private Storage currentStorage;
    private int foodProductPosition;
    private long foodProductId;
    private long storageId;
    private String barcodeValue;
    Button deleteButton;
    private MyRetrofitAPI myRetrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_product);

        addFoodProductNameEditText = findViewById(R.id.addFoodProductNameEditText);

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> saveFoodProduct());

        deleteButton = findViewById(R.id.deleteButton);

        Button scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(view -> openScanActivity());

        myRepository = RepositoryProvider.getInstance(this);
        myRetrofitAPI = MyRetrofitAPI.getInstance();

        Intent intent = getIntent();
        storageId = intent.getLongExtra(TAG.STORAGE_ID, -1);
        currentStorage = myRepository.getStorageById(storageId);
        foodProductPosition = intent.getIntExtra(TAG.FOOD_PRODUCT_POSITION, NO_FOOD_PRODUCT);
        barcodeValue = intent.getStringExtra(TAG.BARCODE_VALUE);

        if (barcodeValue != null) {
            myRetrofitAPI.getOpenFoodFactsResponse(this, barcodeValue);
            Toast.makeText(this, "Barcode: " + barcodeValue, Toast.LENGTH_SHORT).show();
        }


        if (foodProductPosition != NO_FOOD_PRODUCT) {
            deleteButton.setOnClickListener(view -> deleteFoodProduct());
            deleteButton.setVisibility(View.VISIBLE);
            foodProduct = currentStorage.getFoodProducts().get(foodProductPosition);
            foodProductId = foodProduct.getId();
            updateFoodProductUI(foodProduct);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void openScanActivity() {
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(TAG.STORAGE_ID, storageId);
        intent.putExtra(TAG.FOOD_PRODUCT_POSITION, foodProductPosition);
        startActivity(intent);
        finish();
    }

    private void deleteFoodProduct() {
        myRepository.deleteFoodProduct(foodProductId);
        currentStorage.getFoodProducts().remove(foodProduct);
        Toast.makeText(this, "Food product deleted.", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateFoodProductUI(FoodProduct foodProduct) {
        if (foodProduct != null) {
            addFoodProductNameEditText.setText(foodProduct.getName());
        }
    }

    private void saveFoodProduct() {
        String foodProductName = addFoodProductNameEditText.getText().toString();

        if (foodProduct == null) {
            FoodProduct foodProduct = new FoodProduct();
            foodProduct.setStorageId(currentStorage.getId());
            foodProduct.setName(foodProductName);
            foodProduct.setBarCode(barcodeValue);
            myRepository.addFoodProduct(foodProduct);
            currentStorage.getFoodProducts().add(foodProduct);
        } else {
            foodProduct.setName(foodProductName);
            myRepository.editFoodProduct(foodProduct);
        }

        Toast.makeText(this, "Food product saved.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onSuccess(OpenFoodFactsResponse openFoodFactsResponse) {
        String productName = openFoodFactsResponse.getProduct().getFoodProductName();
        addFoodProductNameEditText.setText(productName);
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}