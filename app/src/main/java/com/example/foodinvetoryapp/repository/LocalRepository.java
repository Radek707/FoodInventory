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
        foodProductDao.insert(foodProduct);
    }

    @Override
    public void editFoodProduct(FoodProduct foodProduct) {
        foodProductDao.update(foodProduct);
    }

    @Override
    public void deleteFoodProduct(long id) {
        FoodProduct foodProduct = getFoodProductById(id);
        foodProductDao.delete(foodProduct);
    }

    @Override
    public FoodProduct getFoodProductById(long id) {
        return foodProductDao.getSession().load(FoodProduct.class, id);
    }

    @Override
    public List<FoodProduct> getAllFoodProducts() {
        return foodProductDao.getSession().loadAll(FoodProduct.class);
    }

    @Override
    public void addStorage(Storage storage) {
        storageDao.insert(storage);
    }

    @Override
    public void editStorage(Storage storage) {
        storageDao.update(storage);
    }

    @Override
    public void deleteStorage(long id) {
        Storage storage = getStorageById(id);
        storageDao.delete(storage);
    }

    @Override
    public Storage getStorageById(long id) {
        return storageDao.getSession().load(Storage.class, id);
    }

    @Override
    public List<Storage> getAllStorages() {
        return storageDao.getSession().loadAll(Storage.class);
    }
}
