package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.bean.UserInfo;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.entity.UserInfoEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UserCommitFeedbackRuunable extends TokenCheckedRunnable<StateEntity> {
    private String client;
    private String device;
    private String note;
    private String version;
    public UserCommitFeedbackRuunable(String note, String version){
        this.note = note;
        this.version = version;
        client = "B_androidpad";
        device = TourApplication.instance().getDaoUser().getInnerName()+"androidPad";
    }
    @Override
    public StateEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getmUserService()
                .commiteFeedback(TourApplication.instance().getToken(), client, device, note, version);
    }
    public void onSuccessInBackground(StateEntity result){
//        TourApplication.instance()
//                .updateData(new UserInfo(result.msg.user_mobile, result.msg.inner_name,
//                        result.msg.inner_head));
    }
    @Override
    public void onSuccess(StateEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}
