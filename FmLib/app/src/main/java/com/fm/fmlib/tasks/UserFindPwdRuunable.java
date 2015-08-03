package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.FindPwdEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserFindPwdRuunable extends TokenCheckedRunnable<FindPwdEntity> {
    private String mobile;
    public UserFindPwdRuunable(){}

    public UserFindPwdRuunable(String mobile){
        this.mobile = mobile;
    }
    @Override
    public FindPwdEntity doBackground() throws RetrofitError {
        return FmApplication.instance().getmTotour().getmUserService().findPwd(mobile);
    }

    @Override
    public void onSuccess(FindPwdEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}
