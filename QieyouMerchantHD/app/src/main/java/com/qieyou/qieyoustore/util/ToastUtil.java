package com.qieyou.qieyoustore.util;

import android.widget.Toast;

import com.fm.fmlib.TourApplication;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class ToastUtil {
    public static void showShortToast(String msg){
        Toast.makeText(TourApplication.instance(), msg, Toast.LENGTH_SHORT).show();
    }
}
