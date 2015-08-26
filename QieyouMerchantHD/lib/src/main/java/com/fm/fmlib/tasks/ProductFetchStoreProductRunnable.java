package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tour.entity.ProductBreEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProductFetchStoreProductRunnable extends TokenCheckedRunnable<ProductBreEntity> {
    private  String  items;

     public ProductFetchStoreProductRunnable(String items){
         this.items = items;
     }


    @Override
    public ProductBreEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getProductService().fetchProducts(TourApplication.instance().getToken(), items);
    }



    @Override
    public void onSuccess(ProductBreEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
        this.getBus().post(new ProductState.InnFetchProductBreEvent(result.msg));
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}
