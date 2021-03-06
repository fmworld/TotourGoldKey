package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.LoginOutEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserLoginOutRuunable extends TokenCheckedRunnable<LoginOutEntity> {
    public UserLoginOutRuunable(){}

    @Override
    public LoginOutEntity doBackground() throws RetrofitError {
        return FmApplication.instance().getmTotour().getmUserService().loginOut(FmApplication.instance().getToken());
    }

    @Override
    public void onSuccess(LoginOutEntity result) {
        Log.v(TAG, "result errorInfo "+result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
