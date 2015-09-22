package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.R;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.bean.UserInfo;
import com.fm.fmlib.tour.entity.LoginResetPwdEntity;
import com.fm.fmlib.tour.entity.UserInfoEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserFetchUserInfoRuunable extends TokenCheckedRunnable<UserInfoEntity> {

    @Override
    public UserInfoEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUserService().fetchUserInfo(TourApplication.instance().getToken());
    }
    public void onSuccessInBackground(UserInfoEntity result){
        TourApplication.instance().updateData(new UserInfo(result.msg.user_mobile, result.msg.inner_name, result.msg.inner_head));
    }
    @Override
    public void onSuccess(UserInfoEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
    }


}
