package com.fm.fmlib.tour.Service;

import com.fm.fmlib.tour.entity.TransferEntity;

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
}
