package com.AstronomyDevelopers.photosaying.base;

import android.app.Application;
import android.content.Context;

import com.AstronomyDevelopers.photosaying.database.GreenDaoHelper;

public class BaseApplication extends Application {

    private static Context context;
    private static GreenDaoHelper greenDaoHelper;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        greenDaoHelper = GreenDaoHelper.getInstance();
    }

    public static Context getContext() {
        return context;
    }

    public static GreenDaoHelper getGreenDaoHelper() {
        return greenDaoHelper;
    }

}
