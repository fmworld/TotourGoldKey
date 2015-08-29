package com.qieyou.qieyoustore.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.qieyou.qieyoustore.R;

/**
 * Created by zhoufeng'an on 2015/8/27.
 */
public class PackageInfoUtl {
    public static String getVersion(Context context) {
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
}
