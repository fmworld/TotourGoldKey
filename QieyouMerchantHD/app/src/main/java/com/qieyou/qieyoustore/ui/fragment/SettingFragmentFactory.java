package com.qieyou.qieyoustore.ui.fragment;

import android.app.Fragment;

import com.fm.fmlib.controllers.MainController.HomeMenu;
import com.fm.fmlib.state.UserState;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class SettingFragmentFactory {
    public static Fragment create(String type) {
        if (UserState.Setting.about.toString().equals(type)) {
            return SetAboutFragment.create();
        }

        if (UserState.Setting.changepwd.toString().equals(type)) {
            return SetChangepwdFragment.create();
        }

        if (UserState.Setting.feedback.toString().equals(type)) {
            return SetFeedbackFragment.create();
        }

        return null;
    }


}
