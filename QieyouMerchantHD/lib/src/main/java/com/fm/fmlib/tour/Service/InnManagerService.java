package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.StoreInfoEntity;
import com.fm.fmlib.tour.entity.TransferEntity;
import com.fm.fmlib.tour.entity.UserInfoEntity;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by zhou feng'an on 2015/7/30.
 *
 */

public interface InnManagerService {
    @POST("/manage/index")
    TransferEntity fetchHomePage(@Query("token") String token);



    /**
     * 获取商户信息（数据不全mobile、innname、innhead）
     * @param token
     * @return
     */
    @POST("/inn/info")
    @FormUrlEncoded
    StoreInfoEntity fetchStroeInfo(@Query("token") String token, @Field("inn") String inn_id);
}
