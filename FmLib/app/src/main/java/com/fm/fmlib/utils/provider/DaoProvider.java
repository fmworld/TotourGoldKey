package com.fm.fmlib.utils.provider;

import android.content.Context;

import de.greenrobot.dao.AbstractDao;
import com.fm.fmlib.dao.DaoMaster;
import com.fm.fmlib.dao.DaoSession;

/**
 * Created by zhoufeng'an on 2015/8/4.
 */
public class DaoProvider {
    private static final String DB_NAME ="totour-db";

    public static AbstractDao createUserDao(Context context){
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context.getApplicationContext(), DB_NAME, null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getUserDao();
    }
}
