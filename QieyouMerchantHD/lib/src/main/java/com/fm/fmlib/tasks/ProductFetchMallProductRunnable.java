package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.ProductsEntity;

import retrofit.RetrofitError;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class ProductFetchMallProductRunnable extends TokenCheckedRunnable<ProductsEntity> {
    protected String page;
    private String perpage;
    private String city;
    private String cid;
    private String ccid;
    private String sort;
    private String dest;

    public ProductFetchMallProductRunnable(String page, String perpage, String city, String cid, String ccid, String dest, String sort) {
        this.page = page;
        this.perpage = perpage;
        this.city = city;
        this.cid = cid;
        this.ccid = ccid;
        this.sort = sort;
        this.dest = dest;
    }


    @Override
    public ProductsEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour()
                .getProductService().fetchProducts(TourApplication.instance()
                        .getToken(), page, perpage, city, cid, ccid, dest, sort);
    }


    @Override
    public void onSuccess(ProductsEntity result) {
        Log.v(TAG, "result code " + result.code);
        Log.v(TAG, "result msg " + result.msg);
        Log.v(TAG, "result errorInfo " + result.errorInfo);
//        this.getBus().post(new ProductState.InnFetchProductBreEvent(result.msg));
    }


}
