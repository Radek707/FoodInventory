package com.example.foodinvetoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.foodinvetoryapp.adapters.FoodProductAdapter;
import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.repository.MyRepository;
import com.example.foodinvetoryapp.repository.RepositoryProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StorageDetailsActivity extends AppCompatActivity
        implements FoodProductAdapter.OnFoodProductClickListener {

    private MyRepository myRepository;
    private Storage storage;
    private List<FoodProduct> foodProducts;
    private long storageId;
    private int foodProductPosition = -1;
    private static final int NO_STORAGE = -1;
    private TextView storageNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_details);

        FloatingActionButton addFoodProductButton = findViewById(R.id.addFoodProductButton);
        addFoodProductButton.setOnClickListener(view -> openAddFoodProductActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();

        myRepository = RepositoryProvider.getInstance(this);

        Intent intent = getIntent();
        storageId = intent.getLongExtra(TAG.STORAGE_ID, NO_STORAGE);

        storage = myRepository.getStorageById(storageId);

        storageNameTextView = findViewById(R.id.storageNameTextView);
        storageNameTextView.setText(storage.getStorageName());
        storageNameTextView.setOnClickListener(view -> openAddStorageActivity());

        foodProducts = storage.getFoodProducts();

        RecyclerView recyclerView = findViewById(R.id.foodProductsRecyclerView);
        recyclerView.setAdapter(new FoodProductAdapter(this,
                foodProducts,
                this));
    }

    private void openAddFoodProductActivity() {
        Intent intent = new Intent(this, AddFoodProductActivity.class);
        intent.putExtra(TAG.STORAGE_ID, storageId);
        intent.putExtra(TAG.FOOD_PRODUCT_POSITION, foodProductPosition);
        foodProductPosition = -1; //resetting position to non existing
        startActivity(intent);
    }

    private void openAddStorageActivity() {
        Intent intent = new Intent(this, AddStorageActivity.class);
        intent.putExtra(TAG.STORAGE_ID, storageId);
        startActivity(intent);
    }

    @Override
    public void onClick(int position) {
        foodProductPosition = position;
        openAddFoodProductActivity();
    }
}