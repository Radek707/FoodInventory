package com.example.foodinvetoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.repository.MyRepository;
import com.example.foodinvetoryapp.repository.RepositoryProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class StorageDetailsActivity extends AppCompatActivity {

    private MyRepository myRepository;
    private Storage storage;
    private List<FoodProduct> foodProducts;
    private long storageId;
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
    }

    private void openAddFoodProductActivity() {
        Intent intent = new Intent(this, AddFoodProductActivity.class);
        startActivity(intent);
    }

    private void openAddStorageActivity() {
        Intent intent = new Intent(this, AddStorageActivity.class);
        intent.putExtra(TAG.STORAGE_ID, storageId);
        startActivity(intent);
    }
}