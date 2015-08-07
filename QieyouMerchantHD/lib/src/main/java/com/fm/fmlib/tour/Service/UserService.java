package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.GetVeriCodeEntity;
import com.fm.fmlib.tour.entity.LoginEntity;
import com.fm.fmlib.tour.entity.LoginOutEntity;
import com.fm.fmlib.tour.entity.LoginResetPwdEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zhou feng'an on 2015/7/30.
 *
 */

public interface UserService {
    /**
     * 登录
     * @param name
     * @param pwd
     * @return
     */
    @FormUrlEncoded
    @POST("/login/userLogin")
    LoginEntity loginIn(@Field("name")String name, @Field("password") String pwd);

    /**
     * 找回密码
     * @param mobile
     * @return
     */
    @FormUrlEncoded
    @POST("/login/forgotPassSMS")
    GetVeriCodeEntity findPwd(@Field("mobile")String mobile);

    /**
     * 登出
     * @param token
     * @return
     */
    @POST("/login/logout")
    LoginOutEntity loginOut(@Query("token") String token);


    @POST("/login/forgetPwd")
    @FormUrlEncoded
    LoginResetPwdEntity changePassword(@Field("mobile") String mobile, @Field("verifycode") String code, @Field("password") String password);
}
