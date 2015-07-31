package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;
import com.fm.fmlib.tour.entity.GoddsDetailEntity;
import com.fm.fmlib.tour.entity.GoodsHomeEntity;
import com.fm.fmlib.tour.params.GoodsFetchHomeParams;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public class GoodsFetchDetailRunnable extends NetworkCallRunnable<GoddsDetailEntity>{
    private String item_id;
    public GoodsFetchDetailRunnable(String item_id){
        this.item_id = item_id;
    }

    @Override
    public GoddsDetailEntity doBackgroundCall() throws BaseError {
        return FmApplication.instance().getmTotour().getmGoodsService().fetchGoodsDetail(item_id);
    }

    @Override
    public void onSuccess(GoddsDetailEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result note "+result.msg.note);
    }

    @Override
    public void onError(BaseError be) {

    }
}
