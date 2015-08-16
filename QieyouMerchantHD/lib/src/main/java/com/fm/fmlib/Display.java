package com.fm.fmlib;

import android.os.Bundle;

import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.tour.bean.ProductInfo;

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

    void showHomeSecondContent(MainController.HomeMenu menu);

    void showHomeSecondContent(MainController.HomeMenu menu, Bundle bundle);

    void hideHomeSecondContent();

    void hideHomeMenu();

    void showManagerPage(String url);

    //显示分享列表
    void showShareUI(String thunm, String name, String url);

    //显示支付选择页面
    void showPaymentType(String url);

    void showProductInfo(ProductInfo info);


}
