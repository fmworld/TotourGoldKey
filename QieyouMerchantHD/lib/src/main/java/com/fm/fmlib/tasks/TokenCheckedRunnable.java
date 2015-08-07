package com.fm.fmlib.tasks;

import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.network.client.BaseError;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public abstract class TokenCheckedRunnable<R> extends NetworkCallRunnable<R> {
    public abstract R doBackground();
    public R doBackgroundCall() throws RetrofitError {
        return doBackground();
    }
}
