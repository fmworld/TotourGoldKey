package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.ProCommentsEntity;
import com.fm.fmlib.tour.entity.ProductDetailEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProductFetchCommentsRunnable extends TokenCheckedRunnable<ProCommentsEntity> {
    private String page;
    private String perpage;
    private String productId;

    public ProductFetchCommentsRunnable(String productId, String page, String perpage) {
         this.page = page;
        this.perpage = perpage;
        this.productId = productId;
    }


    @Override
    public ProCommentsEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getProductService().fetchProComments(TourApplication.instance().getToken(),productId,"all", page,perpage);
    }


    @Override
    public void onSuccess(ProCommentsEntity result) {
        Log.v(TAG, "result code " + result.code);
        Log.v(TAG, "result msg " + result.msg);
        Log.v(TAG, "result errorInfo " + result.errorInfo);
//        this.getBus().post(new ProductState.InnFetchProductBreEvent(result.msg));
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError " + be);
    }
}
