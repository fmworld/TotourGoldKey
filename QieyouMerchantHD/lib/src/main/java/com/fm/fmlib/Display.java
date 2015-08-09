package com.fm.fmlib;

import com.fm.fmlib.controllers.MainController;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public interface Display {
    //进入登录页面
    void showLogin();
    //进入找回密码页面
    void showFindPassword();
    //进入主页
    void showHomePage();
    //显示获取验证码的读秒
    void showVeriCodeCountdown();

    void showHomeProfileItem(MainController.HomeMenu menu);

    void hideHomeProfile();

    void showHomeMenuItem(MainController.HomeMenu menu);

    void hideHomeMenu();
}
