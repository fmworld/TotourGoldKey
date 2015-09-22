package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.ShelvedEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 获取商品评论详情
 */
public class GoodsFetchCommentDetailRunnable extends TokenCheckedRunnable<ShelvedEntity> {
    private String comment_id;
    public GoodsFetchCommentDetailRunnable(String comment_id){
        this.comment_id = comment_id;
    }

    @Override
    public ShelvedEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmGoodsService().fetchGoodsCommentDetail(comment_id);
    }

    @Override
    public void onSuccess(ShelvedEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
    }

}
