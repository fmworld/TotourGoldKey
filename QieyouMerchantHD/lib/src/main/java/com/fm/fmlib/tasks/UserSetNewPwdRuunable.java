package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.entity.LoginOutEntity;
import com.fm.fmlib.tour.entity.LoginResetPwdEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserSetNewPwdRuunable extends TokenCheckedRunnable<LoginResetPwdEntity> {
    private String mobile;
    private String code;
    private String pwd;
    public UserSetNewPwdRuunable(){}

    public UserSetNewPwdRuunable(String mobile, String code, String pwd){
        this.mobile = mobile;
        this.code = code;
        this.pwd = pwd;
    }
    @Override
    public LoginResetPwdEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUserService().changePassword(mobile, code, pwd);
    }

    @Override
    public void onSuccess(LoginResetPwdEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
        this.getBus().post(new UserState.UserResetPasswordEvent());
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}