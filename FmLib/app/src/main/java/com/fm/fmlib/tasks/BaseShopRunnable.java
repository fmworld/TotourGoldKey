package com.fm.fmlib.tasks;


import com.fm.fmlib.network.NetworkCallRunnable;

import retrofit.RetrofitError;

/**
 * Created by qieyou on 2015/7/30.
 */
public abstract class BaseShopRunnable<R> extends NetworkCallRunnable<R>{
    public void onPreTourCall() {
    }

    public abstract R doBackgroundCall() throws RetrofitError;

    public abstract void onSuccess(R result);

    public abstract void onError(RetrofitError be);

    public void onFinished() {}
}
