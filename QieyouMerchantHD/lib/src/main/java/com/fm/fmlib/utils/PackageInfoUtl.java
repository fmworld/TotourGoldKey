package com.fm.fmlib.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.fm.fmlib.R;


/**
 * Created by zhoufeng'an on 2015/8/27.
 */
public class PackageInfoUtl {
    public static String getVersionName(Context context) {
            try {
                     PackageManager manager = context.getPackageManager();
                    PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);

                    String version = info.versionName;
                     return version;
                 } catch (Exception e) {
                     e.printStackTrace();
                     return context.getString(R.string.version_failed);
                 }
         }

    public static int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
