package com.fm.fmlib.utils.provider;

import android.content.Context;

import de.greenrobot.dao.AbstractDao;
import com.fm.fmlib.dao.DaoMaster;
import com.fm.fmlib.dao.DaoSession;
import com.fm.fmlib.dao.UserDao;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class DaoProvider {
    private static final String DB_NAME ="totour-db";

    public static UserDao createUserDao(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getUserDao();
    }

    public static DaoSession provideDaoSession(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        return daoMaster.newSession();
    }
}
