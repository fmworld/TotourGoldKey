package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.tour.entity.TransferEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class ProductChangeShelfStateRunnable extends TokenCheckedRunnable<StateEntity> {
    String tag_id;
    String product_id;
     String action;
    public ProductChangeShelfStateRunnable(String tag_id,String product_id ,String action){
        this.tag_id = tag_id;
        this.product_id = product_id;
        this.action = action;
    }
    @Override
    public StateEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getProductService().changeShelfState(tag_id,product_id,action );
    }

    @Override
    public void onSuccessInBackground(StateEntity result){
        TourApplication.instance().getDaoProperty().saveProperty(InnController.InnTransfer.managerHome.toString(), result.msg);
    }

    @Override
    public void onSuccess(StateEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
//        this.getBus().post(new HomeState.HomeManagerFetchEvent(result.msg));
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
