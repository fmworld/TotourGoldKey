package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;
import com.fm.fmlib.tour.entity.ShelvedEntity;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 获取商品评论详情
 */
public class GoodsFetchCommentDetailRunnable extends NetworkCallRunnable<ShelvedEntity>{
    private String comment_id;
    public GoodsFetchCommentDetailRunnable(String comment_id){
        this.comment_id = comment_id;
    }

    @Override
    public ShelvedEntity doBackgroundCall() throws BaseError {
        return FmApplication.instance().getmTotour().getmGoodsService().fetchGoodsCommentDetail(comment_id);
    }

    @Override
    public void onSuccess(ShelvedEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
    }

    @Override
    public void onError(BaseError be) {

    }
}
