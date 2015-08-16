package com.qieyou.qieyoustore.util;

import android.os.Environment;

/**
 * Created by zhoufeng'an on 2015/8/14.
 */
public class TourPicConfig {
    public static final int REQUEST_CODE_PIC_CAMERA = 18;
    public static final int REQUEST_CODE_PIC_LOCAL = 19;
    /**
     * 是否装载了SD卡
     *
     * @return
     */
    public static boolean hasSDCard() {
        if (android.os.Environment.getExternalStorageState().equals
                (android.os.Environment.MEDIA_MOUNTED))
            return true;
        else
            return false;
    }

    /**
     * 获取SD卡 跟路径，如果sd不存在直接写入data目录下面
     *
     * @return
     */
    public static String getRootFilePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath()+ "/";
            //filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/";
             //filePath:/data/data/
        }
    }
}
