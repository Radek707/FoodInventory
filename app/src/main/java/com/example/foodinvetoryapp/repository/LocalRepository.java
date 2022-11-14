package com.example.foodinvetoryapp.repository;

import android.app.Activity;

import com.example.foodinvetoryapp.FoodInvetoryApp;
import com.example.foodinvetoryapp.models.DaoSession;
import com.example.foodinvetoryapp.models.FoodProduct;
import com.example.foodinvetoryapp.models.FoodProductDao;
import com.example.foodinvetoryapp.models.Storage;
import com.example.foodinvetoryapp.models.StorageDao;

import java.util.List;

public class LocalRepository implements MyRepository{
    private DaoSession daoSession;
    private StorageDao storageDao;
    private FoodProductDao foodProductDao;
    private static LocalRepository instance;

    private LocalRepository(Activity activity) {
        daoSession = ((FoodInvetoryApp)activity.getApplication()).getDaoSession();
        storageDao = daoSession.getStorageDao();
        foodProductDao = daoSession.getFoodProductDao();
    }

    public static LocalRepository getInstance(Activity activity) {
        if (instance == null) {
            instance = new LocalRepository(activity);
        }
        return instance;
    }

    @Override
    public void addFoodProduct(FoodProduct foodProduct) {

    }

    @Override
    public void editFoodProduct(FoodProduct foodProduct) {

    }

    @Override
    public void deleteFoodProduct(long id) {

    }

    @Override
    public FoodProduct getFoodProductById(long id) {
        return null;
    }

    @Override
    public List<FoodProduct> getAllFoodProducts() {
        return null;
    }

    @Override
    public void addStorage(Storage storage) {

    }

    @Override
    public void editStorage(Storage storage) {

    }

    @Override
    public void detleteStorage(Storage storage) {

    }

    @Override
    public List<Storage> getAllStorages() {
        return null;
    }
}
