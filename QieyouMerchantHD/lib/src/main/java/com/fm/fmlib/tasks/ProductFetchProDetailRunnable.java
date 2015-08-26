package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.ProductDetailEntity;
import com.fm.fmlib.tour.entity.ProductsEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProductFetchProDetailRunnable extends TokenCheckedRunnable<ProductDetailEntity> {
    private String item;


    public ProductFetchProDetailRunnable(String item) {
        this.item = item;
    }


    @Override
    public ProductDetailEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getProductService().fetchProductDetail(TourApplication.instance().getToken(),item);
    }


    @Override
    public void onSuccess(ProductDetailEntity result) {
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
