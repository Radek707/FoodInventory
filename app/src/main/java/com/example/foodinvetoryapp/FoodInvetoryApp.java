package com.example.foodinvetoryapp;

import android.app.Application;

import com.example.foodinvetoryapp.models.DaoMaster;
import com.example.foodinvetoryapp.models.DaoSession;

import org.greenrobot.greendao.database.Database;

public class FoodInvetoryApp extends Application {
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(this, "food-db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
