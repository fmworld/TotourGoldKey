package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.entity.GetVeriCodeEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserGetVerifyCodeRuunable extends TokenCheckedRunnable<GetVeriCodeEntity> {
    private String mobile;
    public UserGetVerifyCodeRuunable(){}

    public UserGetVerifyCodeRuunable(String mobile){
        this.mobile = mobile;
    }
    @Override
    public GetVeriCodeEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUserService().findPwd(mobile);
    }

    @Override
    public void onSuccess(GetVeriCodeEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
        this.getBus().post(new UserState.UserLoginAccessCodeEvent());
    }
}
