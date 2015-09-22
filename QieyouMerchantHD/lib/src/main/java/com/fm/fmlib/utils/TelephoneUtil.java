package com.fm.fmlib.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.fm.fmlib.TourApplication;

/**
 * Created by zhoufeng'an on 2015/9/21.
 */
public class TelephoneUtil {
    public static String getImei(){
        String imei = ((TelephonyManager) TourApplication.instance().getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
        return imei;
    }
}
