package com.qieyou.qieyoustore.ui.fragment;

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

        if(HomeMenu.MANAGER == menu){
            return HomeManager.create();
        }

        if(HomeMenu.CODE == menu){
            return HomeCodeInput.create();
        }

        if(HomeMenu.MALL == menu){
            return HomeMall.create();
        }

        if(HomeMenu.STORE_GALLERY == menu){
            return HomeStoreViewPager.create();
        }
        if(HomeMenu.MGR_PRO_AE == menu){
            return ProductAddEdit.create();
        }
        if(HomeMenu.STORE_SUDOKU == menu){
            return HomeStoreSudoku.create();
        }

        if(HomeMenu.PRO_DETAIL == menu){
            return HomeProDetail.create();
        }

        if(HomeMenu.COMMENTS == menu){
            return ProductComments.create();
        }

        if(HomeMenu.VERIFY_SUCCESS == menu){
            return HomeCodeSuccess.create();
        }

        if(HomeMenu.WEB == menu){
            return HomeTownTraver.create();
        }
        return null;
    }


}
