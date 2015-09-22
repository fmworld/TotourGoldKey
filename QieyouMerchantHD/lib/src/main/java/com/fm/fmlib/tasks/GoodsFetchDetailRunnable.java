package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.GoddsDetailEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoodsFetchDetailRunnable extends TokenCheckedRunnable<GoddsDetailEntity> {
    private String item_id;

    public GoodsFetchDetailRunnable(String item_id) {
        this.item_id = item_id;
    }

    @Override
    public GoddsDetailEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmGoodsService().fetchGoodsDetail(item_id);
    }

    @Override
    public void onSuccess(GoddsDetailEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code " + result.code);
        Log.v(TAG, "result note " + result.msg.note);
    }


}
