package com.fm.fmlib.controllers;

import com.fm.fmlib.models.UtilProvider;
import com.fm.fmlib.utils.BackgroundExecutor;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class Usercontroller {
    private BackgroundExecutor mBackgroundExecutor;

    public Usercontroller(){
        mBackgroundExecutor = UtilProvider.provideMultiThreadExecutor();
    }

}
