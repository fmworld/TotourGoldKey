package com.fm.fmlib;

import android.net.Uri;
import android.os.Bundle;

import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.tour.bean.ProductInfo;

import java.util.List;

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

    void showHomeMenuItem(MainController.HomeMenu menu, String data);

    void showHomeSecondContent(MainController.HomeMenu menu);

    void showHomeSecondContent(MainController.HomeMenu menu, Bundle bundle);

    void hideHomeSecondContent();

    void showHomeThirdContent(MainController.HomeMenu menu, Bundle bundle);

    void hideHomeThirdContent();

    void hideHomeMenu();

    void showManagerPage(String url);

    //显示分享列表
    void showShareUI(String thunm, String name, String url);

    //显示支付选择页面
    void showWebViewNotify(String url, int type);

    void showProductInfo(ProductInfo info);

    //显示本地选取的产品编辑照片
    void showProLocalImg(Uri uri);

    //商品添加
    void showProductAddSuccessed();

    //编辑成功
    void showProductEditSuccessed();

    //九宫格显示tag分类后的商品
    void showProductBreGrid(List<ProductBreviary> pros);

    //gallery显示tag分类后的商品
    void showProductBreGallery(List<ProductBreviary> pros);

    //显示tag分类后的商品
    void showProductBre(List<ProductBreviary> pros);

    //显示tag分类后的商品
    void showVerifyCode(MainController.HomeMenu menu, Bundle bundle);

    void showSetChangePwd();

    void showSettingItem(String item);
}
