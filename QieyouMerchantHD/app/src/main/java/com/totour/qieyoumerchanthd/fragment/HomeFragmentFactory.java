package com.totour.qieyoumerchanthd.fragment;

import android.app.Fragment;

import com.fm.fmlib.controllers.MainController.HomeMenu;
/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeFragmentFactory {
    public static Fragment create(HomeMenu menu) {
        if (HomeMenu.PERSON_INFO == menu) {
            return HomePersonInfo.create();
        }

        if (HomeMenu.SETTING == menu) {
            return HomeSettingInfo.create();
        }


        return null;
    }
}
