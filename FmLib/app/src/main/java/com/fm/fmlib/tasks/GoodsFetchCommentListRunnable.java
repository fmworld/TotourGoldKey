package com.fm.fmlib.tasks;

import android.util.Log;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.tour.entity.GoddsCommentsEntity;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 * 获取商品评论列表
 */
public class GoodsFetchCommentListRunnable extends TokenCheckedRunnable<GoddsCommentsEntity> {
    private String item_id;
    private String type;
    private String page;
    private String perpage;

    public GoodsFetchCommentListRunnable(String item_id, String type, String page, String perpage) {
        this.item_id = item_id;
        this.type = type;
        this.page = page;
        this.perpage = perpage;
    }

    @Override
    public GoddsCommentsEntity doBackground() throws RetrofitError {
        return FmApplication.instance().getmTotour().getmGoodsService().fetchGoodsComments(item_id, type, page, perpage);
    }

    @Override
    public void onSuccess(GoddsCommentsEntity result) {
        Log.v(TAG, "result errorInfo " + result.errorInfo);
        Log.v(TAG, "result code " + result.code);
        Log.v(TAG, "result size " + result.msg.size());
        for(GoddsCommentsEntity.Comment item : result.msg){
            if(1==item.has_pic){
                Log.v(TAG, "item comment_id " + item.comment_id+" has_pic "+item.has_pic);
            }

            if(0 < item.replyNum){
                Log.v(TAG, "item comment_id " + item.comment_id+" has_reply +++++++++ "+item.replyNum);
            }
        }
    }

    @Override
    public void onError(RetrofitError be) {

    }
}
