package com.fm.fmlib.network;

import com.fm.fmlib.FmApplication;
import com.fm.fmlib.network.NetworkCallRunnable;
import com.fm.fmlib.tour.entity.BaseEntity;
import com.fm.fmlib.tour.entity.LoginEntity;
import com.fm.fmlib.utils.StringUtils;

import retrofit.RetrofitError;

/**
 * Created by zhoufeng'an on 2015/7/31.
 */
public abstract class TokenCheckedRunnable<R> extends NetworkCallRunnable<R> {
    public abstract R doBackground();

    public R doBackgroundCall() throws RetrofitError {
        R result = doBackground();
        BaseEntity entity = (BaseEntity) result;
        if (4004 == entity.code) {
            LoginEntity logined = FmApplication.instance().getmTotour().getmUserService()
                    .loginIn(FmApplication.instance().getAccount(), StringUtils.md5(FmApplication.instance().getPassword()));
            if(1==logined.code)
            {
                FmApplication.instance().setToken(logined.msg.token);
                return doBackground();
            }
            return null;
        }else{
            return result;
        }
    }
}
