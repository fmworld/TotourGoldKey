package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.CodeInfoEntity;
import com.fm.fmlib.tour.entity.ProductDetailEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProductFetchCodeVeriStateRunnable extends TokenCheckedRunnable<CodeInfoEntity> {
    private String item;


    public ProductFetchCodeVeriStateRunnable(String item) {
        this.item = item;
    }


    @Override
    public CodeInfoEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getProductService().fetchCodeVeriState(TourApplication.instance().getToken(),"Android_B_Pad");
    }


    @Override
    public void onSuccess(CodeInfoEntity result) {
        Log.v(TAG, "result code " + result.code);
        Log.v(TAG, "result msg " + result.msg);
        Log.v(TAG, "result errorInfo " + result.errorInfo);
//        this.getBus().post(new ProductState.InnFetchProductBreEvent(result.msg));
    }


}
