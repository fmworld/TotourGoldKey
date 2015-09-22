package com.fm.fmlib.tasks;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.util.Log;

import com.fm.fmlib.R;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.network.TokenCheckedRunnable;
import com.fm.fmlib.state.InnState;
import com.fm.fmlib.tour.Service.UtilService;
import com.fm.fmlib.tour.entity.AppInfoEntity;
import com.fm.fmlib.tour.entity.StateEntity;
import com.fm.fmlib.utils.AlertDialogUtil;
import com.fm.fmlib.utils.DownloadManagerUtil;

import java.io.File;
import java.util.List;

import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

/**
 * Created by zhou feng'an on 2015/7/30.
 */
public class UtilGetAppInfoRunnable extends TokenCheckedRunnable<AppInfoEntity> {
    private String imei;
    protected Context context;

    public UtilGetAppInfoRunnable(Context context, String imei) {
        this.imei = imei;
        this.context = context;
    }

    @Override
    public AppInfoEntity doBackground() throws RetrofitError {
        return TourApplication.instance().getmTotour().getmUtilService().getAppInfo(imei, "keyapk");
    }

    @Override
    public void onSuccess(final AppInfoEntity result) {
        AlertDialogUtil.showAlertDialog(context, context.getString(R.string.update_notify), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DownloadManagerUtil.download(context, Uri.parse(result.msg.app.link));
                dialog.dismiss();
            }
        });
    }
}
