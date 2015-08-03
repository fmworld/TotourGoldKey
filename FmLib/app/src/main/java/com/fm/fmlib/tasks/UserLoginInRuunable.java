package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.entity.LoginEntity;
import com.fm.fmlib.utils.StringUtils;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserLoginInRuunable extends TokenCheckedRunnable<LoginEntity> {
    private String name;
    private String pwd; //MD5加密过后的
    public UserLoginInRuunable(){}

    public UserLoginInRuunable(String name, String pwd){
        this.name = name;
        this.pwd = StringUtils.md5(pwd);

    }
    @Override
    public LoginEntity doBackground() throws RetrofitError {
        return FmApplication.instance().getmTotour().getmUserService().loginIn(name, pwd);
    }

    @Override
    public void onSuccess(LoginEntity result) {
        Log.v("totour0888", "result errorInfo "+result.errorInfo);
        Log.v("totour0888", "result code "+result.code);
        Log.v("totour0888", "result msg "+result.msg);

        FmApplication.instance().getTempUserInfo().token = result.msg.token;
        this.getBus().post(new UserState.UserLoginExecutedEvent(-1, name, pwd));
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
