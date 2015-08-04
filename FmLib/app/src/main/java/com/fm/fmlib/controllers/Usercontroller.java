package com.fm.fmlib.controllers;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.models.UtilProvider;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.utils.BackgroundExecutor;
import com.squareup.otto.Subscribe;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserController {
    public UserController(){
    }

    public void atachUI(){
        FmApplication.instance().getmBus().register(this);
    }

    public  void unatachUI(){
        FmApplication.instance().getmBus().unregister(this);
    }

    @Subscribe public void uiLoginSuccessed(UserState.UserLoginExecutedEvent event){
        Log.v("totour0888", "loginExecuted  " + event.callingId + " name "+event.name + "  pwd  "+event.pwd);
    }
}
