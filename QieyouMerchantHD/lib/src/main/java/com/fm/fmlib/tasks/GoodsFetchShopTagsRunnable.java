package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.ShopTagsEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoodsFetchShopTagsRunnable extends TokenCheckedRunnable<ShopTagsEntity> {
    private String page;
    private String perpage;
    private String last_id;
    public GoodsFetchShopTagsRunnable(String page, String perpage, String last_id){
        this.page = page;
        this.perpage = perpage;
        this.last_id = last_id;
    }

    @Override
    public ShopTagsEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmGoodsService().fetchShopTagList(TourApplication.instance().getToken(), page, perpage, last_id);
    }

    @Override
    public void onSuccess(ShopTagsEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg.size());
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
