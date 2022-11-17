package com.example.foodinvetoryapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.foodinvetoryapp.adapters.StorageAdapter;
import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.repository.MyRepository;
import com.example.foodinvetoryapp.repository.RepositoryProvider;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity implements StorageAdapter.OnStorageClickListener{

    private MyRepository myLocalRepository;
    private List<Storage> storages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addStorageButton = findViewById(R.id.addStorageButton);
        addStorageButton.setOnClickListener(view -> openAddStorageActivity());
    }

    @Override
    protected void onResume() {
        super.onResume();

        myLocalRepository = RepositoryProvider.getInstance(this);
        storages = myLocalRepository.getAllStorages();

        RecyclerView recyclerView = findViewById(R.id.storageRecyclerView);
        recyclerView.setAdapter(new StorageAdapter(this, storages, this));
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(this, StorageDetailsActivity.class);
        long id = storages.get(position).getId();
        intent.putExtra(TAG.STORAGE_ID, id);
        startActivity(intent);
    }

    private void openAddStorageActivity() {
        Intent intent = new Intent(this, AddStorageActivity.class);
        startActivity(intent);
    }
}