package com.qieyou.qieyoustore.util;

import com.fm.fmlib.utils.RegularUtil;

/**
 * Created by zhoufeng'an on 2015/8/7.
 */
public class TourRegularUtil {
    public static boolean checkLoginable(String accunt, String pwd){
        return RegularUtil.isMobileNum(accunt)&& RegularUtil.isLengthEnough(pwd, 6);
    }

    public static boolean checkPasswordCanChanged(String mobile, String code, String newPwd){
//        return RegularUtil.isMobileNum(mobile)&&RegularUtil.isLengthEnough(code, 4)&&RegularUtil.checkLetterNumberSpecial(newPwd, 6,20);
        return RegularUtil.isMobileNum(mobile)&&RegularUtil.isLengthEnough(code, 4)&&RegularUtil.isLengthEnough(newPwd, 6);
    }
}

