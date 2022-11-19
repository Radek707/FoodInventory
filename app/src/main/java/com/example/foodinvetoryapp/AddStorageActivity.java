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

import java.util.List;

public class AddStorageActivity extends AppCompatActivity {

    private static final int NO_STORAGE = -1;
    private MyRepository myRepository;
    private Storage storage;
    private long storageId;
    private EditText addStorageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_storage);

        myRepository = RepositoryProvider.getInstance(this);

        Button saveButton = findViewById(R.id.saveButton);
        Button deleteButton = findViewById(R.id.deleteButton);

        saveButton.setOnClickListener(view -> saveStorage());

        addStorageEditText = findViewById(R.id.addStorageEditText);

        Intent intent = getIntent();
        storageId = intent.getLongExtra(TAG.STORAGE_ID, NO_STORAGE);

        if (storageId != NO_STORAGE) {
            deleteButton.setOnClickListener(view -> deleteStorage());
            deleteButton.setVisibility(View.VISIBLE);
            storage = myRepository.getStorageById(storageId);
            updateUI(storage);
        } else {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    private void updateUI(Storage storage) {
        if (storage != null) {
            addStorageEditText.setText(storage.getStorageName());
        }
    }

    private void deleteStorage() {
        List<FoodProduct> foodProducts = myRepository.getStorageById(storageId).getFoodProducts();
        for (FoodProduct foodProduct:foodProducts) {
            myRepository.deleteFoodProduct(foodProduct.getId());
        }
        myRepository.deleteStorage(storageId);
        Toast.makeText(this, "Storage deleted", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void saveStorage() {
        String storageName = addStorageEditText.getText().toString();

        if (storage == null) {
            Storage storage = new Storage();
            storage.setStorageName(storageName);
            myRepository.addStorage(storage);
        } else {
            storage.setStorageName(storageName);
            myRepository.editStorage(storage);
        }

        Toast.makeText(this, "Storage saved.", Toast.LENGTH_SHORT).show();
        finish();
    }
}