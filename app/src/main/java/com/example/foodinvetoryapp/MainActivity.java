package com.example.foodinvetoryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.repository.LocalRepository;
import com.example.foodinvetoryapp.repository.MyRepository;
import com.example.foodinvetoryapp.repository.RepositoryProvider;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MyRepository myLocalRepository;
    private List<Storage> storages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myLocalRepository = RepositoryProvider.getInstance(this);

        Storage storage = new Storage();
        storage.setStorageName("Refrigerator");
        myLocalRepository.addStorage(storage);
        storages = myLocalRepository.getAllStorages();
        Toast.makeText(this, "Storage name: " + storages.get(0), Toast.LENGTH_SHORT).show();
    }
}