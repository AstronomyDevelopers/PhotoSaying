package com.AstronomyDevelopers.photosaying.database;

import android.database.sqlite.SQLiteDatabase;

import com.AstronomyDevelopers.photosaying.base.BaseApplication;
import com.AstronomyDevelopers.photosaying.dao.DaoMaster;
import com.AstronomyDevelopers.photosaying.dao.DaoSession;

public class GreenDaoHelper {

    private static DaoMaster.DevOpenHelper devOpenHelper;
    private static SQLiteDatabase database;
    private static DaoMaster daoMaster;
    private static DaoSession daoSession;
    private static GreenDaoHelper mInstance;

    private GreenDaoHelper() {
        //初始化建议放在Application中进行
        if (mInstance == null) {
            //创建数据库"photosaying.db"
            devOpenHelper = new DaoMaster.DevOpenHelper(BaseApplication.getContext(), "photosaying.db", null);
            //获取可写数据库
            database = devOpenHelper.getWritableDatabase();
            //获取数据库对象
            daoMaster = new DaoMaster(database);
            //获取Dao对象管理者
            daoSession = daoMaster.newSession();
        }
    }


    // 单例模式
    public static GreenDaoHelper getInstance() {
        if (mInstance == null) {
            synchronized (GreenDaoHelper.class) {
                if (mInstance == null) {
                    mInstance = new GreenDaoHelper();
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
