package com.fm.fmlib.tasks;

import android.net.Uri;
import android.util.Log;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.LoginResetPwdEntity;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.utils.FileUtil;

import java.io.File;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UtilUploadProImageRunnable extends TokenCheckedRunnable<StateEntity> {
    private List<Uri> uris;


    public UtilUploadProImageRunnable(List<Uri> uris){
        this.uris = uris;
    }
    @Override
    public StateEntity doBackground() throws RetrofitError {
        String mimeType = "image/jpg";
        TypedFile typedFile;
        StateEntity entity;
        StateEntity result = new StateEntity();
        result.code =1;
        StringBuffer sb = new StringBuffer();
        for(Uri item : uris){
            typedFile = new TypedFile(mimeType, FileUtil.getFileFromUri(item));
            entity = TourApplication.instance().getmTotour().getmUtilService()
                    .uploadData(TourApplication.instance().getToken(), UtilService.UploadType.product.toString(), typedFile);
            if(1 == entity.code&&null != entity.msg &&!"".equals(entity.msg)){
                sb.append(entity.msg).append(",");
            }
            result.code *= entity.code;
        }
        int index = sb.lastIndexOf(",");
        if(index > 0){
            result.msg = sb.substring(0,index);
        }
        return result;
    }

    @Override
    public void onSuccess(StateEntity result) {
        Log.v(TAG, "result code "+result.code);
        Log.v(TAG, "result msg "+result.msg);
        Log.v(TAG, "result errorInfo "+result.errorInfo);
        this.getBus().post(new InnState.InnUploadProImagsEvent(result.msg));
    }

    @Override
    public void onError(RetrofitError be) {
        Log.v(TAG, "BaseError "+be);
    }
}
