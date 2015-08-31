package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.entity.TagListEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProfileAddTagRunnable extends TokenCheckedRunnable<StateEntity> {
    private  String tag_name;
     public ProfileAddTagRunnable(String tag_name){
         this.tag_name = tag_name;
     }


    @Override
    public StateEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUtilService().addNewTag(TourApplication.instance().getToken(),tag_name);
    }

    public void onSuccessInBackground(StateEntity result){
//        if(!result.msg.count.equals(TourApplication.instance().getDaoProperty().getValue(tagsercont))
//                || !result.msg.last_update.equals(TourApplication.instance().getDaoProperty().getValue(tagserupstame))){
//            TourApplication.instance().getDaoProductTag().saveProductTags(result.msg.tags);
//            TourApplication.instance().getDaoProperty().saveProperty(tagsercont, result.msg.count);
//            TourApplication.instance().getDaoProperty().saveProperty(tagserupstame,result.msg.last_update);
//        }
        ProductTag tag = new ProductTag();
        tag.setTag_id(result.msg);
        tag.setTag_name(tag_name);
        TourApplication.instance().getDaoProductTag().saveProductTag(tag);
    }

    @Override
    public void onSuccess(StateEntity result) {
//        Log.v(TAG, "result code "+result.code);
//        Log.v(TAG, "result msg "+result.msg);
//        Log.v(TAG, "result errorInfo "+result.errorInfo);
        this.getBus().post(new ProductState.ProductAddNewTagEvent());
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}
