package com.qieyou.qieyoustore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.view.View;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.network.TourConfig;
import com.qieyou.qieyoustore.fragment.HomeFragmentFactory;
import com.qieyou.qieyoustore.fragment.HomeManager;
import com.qieyou.qieyoustore.fragment.LoginFindpwdFragment;
import com.qieyou.qieyoustore.fragment.LoginInFragment;
import com.qieyou.qieyoustore.util.MyShareUtils;
import com.qieyou.qieyoustore.widget.AnimListenFragment;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class AndroidDisplay implements Display {
    private Activity mActiviyt;

    public AndroidDisplay(Activity mActivyt) {
        this.mActiviyt = mActivyt;
    }

    @Override
    public void showLogin() {
        Fragment fragment = LoginInFragment.create();
        mActiviyt.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_main, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void showFindPassword() {
        Fragment fragment = LoginFindpwdFragment.create();
        mActiviyt.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_main, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void showHomePage() {
        mActiviyt.startActivity(new Intent(mActiviyt, HomeAcitvity.class));
    }

    @Override
    public void showVeriCodeCountdown() {
        ((LoginActivity) mActiviyt).showCodeCountDown();
    }

    @Override
    public void showHomeProfileItem(MainController.HomeMenu menu) {
        if (View.GONE == mActiviyt.findViewById(R.id.home_menu_layout).getVisibility()
                || ((HomeAcitvity) mActiviyt).getCurrentMenuTag() != menu) {
            mActiviyt.findViewById(R.id.home_menu_layout).setVisibility(View.VISIBLE);
            mActiviyt.findViewById(R.id.home_menu_layout).setClickable(true);

            AnimListenFragment fragment = (AnimListenFragment) HomeFragmentFactory.create(menu);
            mActiviyt.getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.home_menu_in_left_to_right, R.anim.home_menu_out_right_to_left)
                    .replace(R.id.home_menu_content, fragment, menu.toString())
                    .commit();
            ((HomeAcitvity) mActiviyt).setCurrentMenuTag(menu);
        } else {
            hideHomeProfile();
        }
    }

    @Override
    public void hideHomeProfile() {
        if (!((HomeAcitvity) mActiviyt).isMeunuFragCanMove()) {
            return;
        }
        ((HomeAcitvity) mActiviyt).setMeunuFragCanMove(false);
        AnimListenFragment fragment = (AnimListenFragment) mActiviyt.getFragmentManager()
                .findFragmentByTag(null != ((HomeAcitvity) mActiviyt).getCurrentMenuTag()
                        ? ((HomeAcitvity) mActiviyt).getCurrentMenuTag().toString() : "");
        if (null != fragment) {
            fragment.setAnimationListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mActiviyt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mActiviyt.findViewById(R.id.home_menu_layout).setClickable(false);
                            ((HomeAcitvity) mActiviyt).setMeunuFragCanMove(true);
                            mActiviyt.findViewById(R.id.home_menu_layout).setVisibility(View.GONE);

                        }
                    });
                }
            });
        }
        mActiviyt.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.home_menu_in_left_to_right, R.anim.home_menu_out_right_to_left)
                .remove(fragment)
                .commit();
        ((HomeAcitvity) mActiviyt).setCurrentMenuTag(null);
    }

    @Override
    public void showHomeMenuItem(MainController.HomeMenu menu) {
        if (menu == MainController.HomeMenu.SETTING) {
            showHomeProfileItem(menu);
            return;
        }

        if (menu == MainController.HomeMenu.STORE) {
            checkMenuState();
            showConttentItem(menu);
            return;
        }

        if (menu == MainController.HomeMenu.MALL) {
            checkMenuState();
            showConttentItem(menu);
            return;
        }

        if (menu == MainController.HomeMenu.CODE) {
            checkMenuState();
            showConttentItem(menu);
            return;
        }

        if (menu == MainController.HomeMenu.MANAGER) {
            checkMenuState();
            showConttentItem(menu);
            return;
        }

    }

    private void checkMenuState() {
        MainController.HomeMenu temp = ((HomeAcitvity) mActiviyt).getCurrentMenuTag();
        if (temp == MainController.HomeMenu.SETTING || temp == MainController.HomeMenu.PERSON_INFO) {
            hideHomeProfile();
        }
    }

    private void showConttentItem(MainController.HomeMenu menu) {
        if (menu != (((HomeAcitvity) mActiviyt).getCurrentContentTag())) {
            Fragment fragment = HomeFragmentFactory.create(menu);
            if (null == fragment) {
                return;
            }
            FragmentTransaction t = mActiviyt.getFragmentManager()
                    .beginTransaction();
//                    .setCustomAnimations(R.anim.home_menu_in_left_to_right, R.anim.home_menu_out_right_to_left)
            t.replace(R.id.fragment_content_layout, fragment, menu.toString())
                    .commit();
            ((HomeAcitvity) mActiviyt).setCurrentContentTag(menu);
        }
    }

    @Override
    public void hideHomeMenu() {

    }

    @Override
    public void showManagerPage(String url) {
//        HomeManager manager =((HomeManager) mActiviyt.getFragmentManager()
//                .findFragmentByTag(((HomeAcitvity) mActiviyt).getCurrentContentTag().toString()));
//        if(null != manager){
//            manager.loadUrl(url);
//        }
    }

    @Override
    public void showShareUI(String thunm, String name, String url) {
        MyShareUtils utils = new MyShareUtils(mActiviyt);
        utils.setShareContent(name, "", url, TourConfig.instance().getImageRoot() + "/" + thunm, "", "");
        utils.addCustomPlatforms();
    }
}
