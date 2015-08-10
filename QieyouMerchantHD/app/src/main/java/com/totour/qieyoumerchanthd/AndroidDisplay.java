package com.totour.qieyoumerchanthd;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.utils.DisplayUtil;
import com.totour.qieyoumerchanthd.fragment.HomeFragmentFactory;
import com.totour.qieyoumerchanthd.fragment.HomePersonInfo;
import com.totour.qieyoumerchanthd.fragment.LoginFindpwdFragment;
import com.totour.qieyoumerchanthd.fragment.LoginInFragment;
import com.totour.qieyoumerchanthd.widget.AnimListenFragment;

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
                ||((HomeAcitvity)mActiviyt).getCurrentMenuTag() != menu) {
            mActiviyt.findViewById(R.id.home_menu_layout).setVisibility(View.VISIBLE);

            Fragment fragment = HomeFragmentFactory.create(menu);
            mActiviyt.getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.home_menu_in_left_to_right, R.anim.home_menu_out_right_to_left)
                    .replace(R.id.home_menu_content, fragment, menu.toString())
                    .commit();
            ((HomeAcitvity)mActiviyt).setCurrentMenuTag(menu);
        }else {
            hideHomeProfile();
        }
    }

    @Override
    public void hideHomeProfile() {
        AnimListenFragment fragment = (AnimListenFragment)mActiviyt.getFragmentManager()
                .findFragmentByTag(null !=((HomeAcitvity)mActiviyt).getCurrentMenuTag()
                        ?((HomeAcitvity)mActiviyt).getCurrentMenuTag().toString():"");
        if(null != fragment){
            fragment.setAnimationListener(new AnimatorListenerAdapter(){
                @Override
                public void onAnimationEnd(Animator animation)
                {
                    mActiviyt.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
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
        ((HomeAcitvity)mActiviyt).setCurrentMenuTag(null);
    }

    @Override
    public void showHomeMenuItem(MainController.HomeMenu menu) {
        if(menu == MainController.HomeMenu.SETTING){
            showHomeProfileItem(menu);
            return;
        }

        if(menu == MainController.HomeMenu.STORE){
            checkMenuState();
            return;
        }

        if(menu == MainController.HomeMenu.MALL){
            checkMenuState();
            return;
        }

        if(menu == MainController.HomeMenu.CODE){
            checkMenuState();
            return;
        }

        if(menu == MainController.HomeMenu.MANAGER){
            checkMenuState();
            return;
        }

    }

    private void checkMenuState(){
        MainController.HomeMenu temp = ((HomeAcitvity)mActiviyt).getCurrentMenuTag();
        if(temp == MainController.HomeMenu.SETTING || temp == MainController.HomeMenu.PERSON_INFO){
            hideHomeProfile();
        }
    }

    @Override
    public void hideHomeMenu() {

    }
}
