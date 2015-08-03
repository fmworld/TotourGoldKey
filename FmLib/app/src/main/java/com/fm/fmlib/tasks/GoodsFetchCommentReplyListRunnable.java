package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.GoddsCommentReplysEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 获取商品评论回复列表
 */
public class GoodsFetchCommentReplyListRunnable extends TokenCheckedRunnable<GoddsCommentReplysEntity> {
    private String comment_id;
    private String page;
    private String perpage;
    private String item;

    public GoodsFetchCommentReplyListRunnable(String comment_id, String item, String page, String perpage){
        this.comment_id = comment_id;
        this.page = page;
        this.perpage = perpage;
        this.item = item;
    }

    @Override
    public GoddsCommentReplysEntity doBackground() throws RetrofitError {
        return FmApplication.instance().getmTotour().getmGoodsService().fetchGoodsCommentReplyList(comment_id, item, page, perpage);
    }

    @Override
    public void onSuccess(GoddsCommentReplysEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code "+result.code);
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
