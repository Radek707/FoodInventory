package com.example.foodinvetoryapp.repository;

import android.app.Activity;

public class RepositoryProvider {
    public static MyRepository getInstance(Activity activity) {
        return LocalRepository.getInstance(activity);
    }
}
