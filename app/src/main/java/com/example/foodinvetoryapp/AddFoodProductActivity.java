package com.example.foodinvetoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodinvetoryapp.callbacks.APICallback;
import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.models.api_models.OpenFoodFactsResponse;
import com.example.foodinvetoryapp.repository.MyRepository;
import com.example.foodinvetoryapp.repository.RepositoryProvider;
import com.example.foodinvetoryapp.services.MyRetrofitAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFoodProductActivity extends AppCompatActivity implements APICallback<OpenFoodFactsResponse> {

    public static final int NO_FOOD_PRODUCT = -1;
    private FoodProduct foodProduct;
    private MyRepository myRepository;
    private EditText addFoodProductNameEditText, expireDateEditText;
    private TextView nutrientScoreTextView;
    private Storage currentStorage;
    private int foodProductPosition;
    private long foodProductId;
    private long storageId;
    private String barcodeValue;
    private String nutrientScore;
    private String frontImageUrl;
    private Button deleteButton, saveButton;
    private Date expireDate;

    final Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_product);

        initView();

        myRepository = RepositoryProvider.getInstance(this);
        MyRetrofitAPI myRetrofitAPI = MyRetrofitAPI.getInstance();

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

    private void initView() {
        addFoodProductNameEditText = findViewById(R.id.addFoodProductNameEditText);
        nutrientScoreTextView = findViewById(R.id.nutrientScoreTextView);
        expireDateEditText = findViewById(R.id.expireDateEditText);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(view -> saveFoodProduct());
        deleteButton = findViewById(R.id.deleteButton);
        Button scanButton = findViewById(R.id.scanButton);
        scanButton.setOnClickListener(view -> openScanActivity());

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateExpireDate();
            }
        };

        expireDateEditText.setOnClickListener(view -> pickUpDate(date));
    }

    private void pickUpDate(DatePickerDialog.OnDateSetListener date) {
        new DatePickerDialog(AddFoodProductActivity.this,
                date,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateExpireDate() {
        expireDate = myCalendar.getTime();
        formatExpireDateInEditText(myCalendar.getTime());
    }

    private void formatExpireDateInEditText(Date time) {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        expireDateEditText.setText(dateFormat.format(time));
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
            if (nutrientScore != null) {
                nutrientScoreTextView.setText("Nutrient score: " +
                        foodProduct.getNutrientScore().toUpperCase(Locale.ROOT));
            }
            if (foodProduct.getExpireDate() != null) {
                formatExpireDateInEditText(foodProduct.getExpireDate());
            }
        }
    }

    private void saveFoodProduct() {
        String foodProductName = addFoodProductNameEditText.getText().toString();

        if (foodProduct == null) {
            FoodProduct foodProduct = new FoodProduct();
            foodProduct.setStorageId(currentStorage.getId());
            foodProduct.setName(foodProductName);
            foodProduct.setBarCode(barcodeValue);
            foodProduct.setNutrientScore(nutrientScore);
            foodProduct.setExpireDate(expireDate);
            myRepository.addFoodProduct(foodProduct);
            currentStorage.getFoodProducts().add(foodProduct);
        } else {
            foodProduct.setNutrientScore(nutrientScore);
            foodProduct.setBarCode(barcodeValue);
            foodProduct.setExpireDate(expireDate);
            foodProduct.setName(foodProductName);
            myRepository.editFoodProduct(foodProduct);
        }
        Toast.makeText(this, "Food product saved.", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onSuccess(OpenFoodFactsResponse openFoodFactsResponse) {
        if (!openFoodFactsResponse.getStatus().equals("product not found")) {
            String productName = openFoodFactsResponse.getProduct().getFoodProductName();
            addFoodProductNameEditText.setText(productName);
            nutrientScore = openFoodFactsResponse.getProduct().getNutrientScore();
            frontImageUrl = openFoodFactsResponse.getProduct().getFrontImageUrl();
        } else {
            Toast.makeText(this, "product not found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(String message) {
        Toast.makeText(this, "Error: " + message, Toast.LENGTH_SHORT).show();
    }
}