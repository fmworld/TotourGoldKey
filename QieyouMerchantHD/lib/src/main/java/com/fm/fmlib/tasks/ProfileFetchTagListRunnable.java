package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.entity.LoginResetPwdEntity;
import com.fm.fmlib.tour.entity.TagListEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProfileFetchTagListRunnable extends TokenCheckedRunnable<TagListEntity> {
    private final String  tagsercont ="tagsercont";
    private final String  tagserupstame ="tagserupstame";
     public ProfileFetchTagListRunnable(){}


    @Override
    public TagListEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUtilService().fetchTagList(TourApplication.instance().getToken(),"","1");
    }

    public void onSuccessInBackground(TagListEntity result){
//        if(!result.msg.count.equals(TourApplication.instance().getDaoProperty().getValue(tagsercont))
//                || !result.msg.last_update.equals(TourApplication.instance().getDaoProperty().getValue(tagserupstame))){
//            TourApplication.instance().getDaoProductTag().saveProductTags(result.msg.tags);
//            TourApplication.instance().getDaoProperty().saveProperty(tagsercont, result.msg.count);
//            TourApplication.instance().getDaoProperty().saveProperty(tagserupstame,result.msg.last_update);
//        }
        TourApplication.instance().getDaoProductTag().saveProductTags(result.msg);
    }

    @Override
    public void onSuccess(TagListEntity result) {
//        Log.v(TAG, "result code "+result.code);
//        Log.v(TAG, "result msg "+result.msg);
//        Log.v(TAG, "result errorInfo "+result.errorInfo);
        this.getBus().post(new ProductState.ProductFetchTagListEvent());
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}
