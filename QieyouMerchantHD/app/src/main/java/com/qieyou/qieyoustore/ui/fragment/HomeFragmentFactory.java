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
            return HomeCode.create();
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
        return null;
    }


}
