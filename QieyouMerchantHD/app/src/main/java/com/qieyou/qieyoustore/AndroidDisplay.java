package com.qieyou.qieyoustore;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.fm.fmlib.Display;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.TourConfig;
import com.fm.fmlib.tour.bean.ProductInfo;
import com.qieyou.qieyoustore.ui.fragment.HomeFragmentFactory;
import com.qieyou.qieyoustore.ui.fragment.HomeStoreFragment;
import com.qieyou.qieyoustore.ui.fragment.HomeStoreSudoku;
import com.qieyou.qieyoustore.ui.fragment.LoginFindpwdFragment;
import com.qieyou.qieyoustore.ui.fragment.LoginInFragment;
import com.qieyou.qieyoustore.ui.fragment.ProductAddEdit;
import com.qieyou.qieyoustore.ui.fragment.SetAboutFragment;
import com.qieyou.qieyoustore.ui.fragment.SetChangepwdFragment;
import com.qieyou.qieyoustore.ui.fragment.SettingFragmentFactory;
import com.qieyou.qieyoustore.ui.widget.ShareInfoDialog;
import com.qieyou.qieyoustore.util.MyShareUtils;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class AndroidDisplay implements Display {
    private Activity mActiviyt;
    private ShareInfoDialog shareInfoDialog;

    public AndroidDisplay(Activity mActivyt) {
        this.mActiviyt = mActivyt;
    }

    @Override
    public void showLoginActivity() {
        mActiviyt.startActivity(new Intent(mActiviyt, LoginActivity.class));
        mActiviyt.finish();
    }

    @Override
    public void showLogin() {
        Fragment fragment = LoginInFragment.create();
        mActiviyt.getFragmentManager().beginTransaction()
                .replace(R.id.fragment_main, fragment, fragment.getClass().getSimpleName())
                .commit();
    }

//    @Override
//    public void showCodeCountDown() {
//        LoginFindpwdFragment fragment = (LoginFindpwdFragment) mActiviyt.getFragmentManager()
//                .findFragmentByTag(LoginFindpwdFragment.class.getSimpleName());
//        if (null != fragment) {
//            fragment.showGetVericodeCountDown();
//        }
//    }

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
        mActiviyt.finish();
    }

    @Override
    public void showVeriCodeCountdown() {
        LoginFindpwdFragment fragment = (LoginFindpwdFragment) mActiviyt.getFragmentManager()
                .findFragmentByTag(LoginFindpwdFragment.class.getSimpleName());
        if (null != fragment) {
            fragment.showGetVericodeCountDown();
        }
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
    public boolean hideHomeProfile() {
        if (!((HomeAcitvity) mActiviyt).isMeunuFragCanMove()) {
            return false;
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
        } else {
            return false;
        }
        int state = mActiviyt.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.home_menu_in_left_to_right, R.anim.home_menu_out_right_to_left)
                .remove(fragment)
                .commit();
        if (state < 0) {
            ((HomeAcitvity) mActiviyt).setCurrentMenuTag(null);
        }
        return state < 0;
    }

    @Override
    public void showHomeMenuItem(MainController.HomeMenu menu) {
        if (menu == MainController.HomeMenu.SETTING) {
            showHomeProfileItem(menu);
            return;
        }

        if (menu == MainController.HomeMenu.STORE_GALLERY) {
            checkMenuState();
            showConttentItem(menu);
            return;
        }

        if (menu == MainController.HomeMenu.STORE_SUDOKU) {
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
            mActiviyt.startActivity(new Intent(mActiviyt, CodeActivity.class));
            return;
        }

        if (menu == MainController.HomeMenu.MANAGER) {
            checkMenuState();
            showConttentItem(menu);
            return;
        }

    }

    @Override
    public void showHomeMenuItem(MainController.HomeMenu menu, String data) {
        if (menu == MainController.HomeMenu.WEB) {
            checkScMenuState();
            Bundle bundle = new Bundle();
            bundle.putString("link", data);
            showHomeSecondContent(menu, bundle);
            return;
        } else {
            showHomeMenuItem(menu);
        }
    }

    @Override
    public void showHomeSecondContent(MainController.HomeMenu menu) {


        if (View.GONE == mActiviyt.findViewById(R.id.fragment_content_second_layout).getVisibility()
                || ((HomeAcitvity) mActiviyt).getCurrentSContentTag() != menu) {
            mActiviyt.findViewById(R.id.fragment_content_second_layout).setVisibility(View.VISIBLE);

            AnimListenFragment fragment = (AnimListenFragment) HomeFragmentFactory.create(menu);
            mActiviyt.getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.home_scontent_in_right_to_left, R.anim.home_scontent_out_left_to_right)
                    .replace(R.id.fragment_content_second_layout, fragment, menu.toString())
                    .commit();
            ((HomeAcitvity) mActiviyt).setCurrentSContentTag(menu);
        } else {
            hideHomeSecondContent();
        }
    }

    @Override
    public void showHomeSecondContent(MainController.HomeMenu menu, Bundle bundle) {
        if (View.GONE == mActiviyt.findViewById(R.id.fragment_content_second_layout).getVisibility()
                || ((HomeAcitvity) mActiviyt).getCurrentSContentTag() != menu) {
            mActiviyt.findViewById(R.id.fragment_content_second_layout).setVisibility(View.VISIBLE);

            AnimListenFragment fragment = (AnimListenFragment) HomeFragmentFactory.create(menu);
            fragment.setArguments(bundle);
            mActiviyt.getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.home_scontent_in_right_to_left, R.anim.home_scontent_out_left_to_right)
                    .replace(R.id.fragment_content_second_layout, fragment, menu.toString())
                    .commit();
            ((HomeAcitvity) mActiviyt).setCurrentSContentTag(menu);
        }
//        else {
//            hideHomeSecondContent();
//        }
    }

    @Override
    public boolean hideHomeSecondContent() {
        AnimListenFragment fragment = (AnimListenFragment) mActiviyt.getFragmentManager()
                .findFragmentByTag(null != ((HomeAcitvity) mActiviyt).getCurrentSContentTag()
                        ? ((HomeAcitvity) mActiviyt).getCurrentSContentTag().toString() : "");
        if (null == fragment) {
            return false;
        }
        fragment.setAnimationListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mActiviyt.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActiviyt.findViewById(R.id.fragment_content_second_layout).setVisibility(View.GONE);
                    }
                });
            }
        });
        int state = mActiviyt.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.home_scontent_in_right_to_left, R.anim.home_scontent_out_left_to_right)
                .remove(fragment)
                .commit();
        if (state < 0) {
            ((HomeAcitvity) mActiviyt).setCurrentSContentTag(null);
        }
        return state < 0;
    }

    @Override
    public void showHomeThirdContent(MainController.HomeMenu menu, Bundle bundle) {
        if (View.GONE == mActiviyt.findViewById(R.id.fragment_content_third_layout).getVisibility()
                || ((HomeAcitvity) mActiviyt).getCurrentThirdContentTag() != menu) {
            mActiviyt.findViewById(R.id.fragment_content_third_layout).setVisibility(View.VISIBLE);

            AnimListenFragment fragment = (AnimListenFragment) HomeFragmentFactory.create(menu);
            fragment.setArguments(bundle);
            mActiviyt.getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.anim.home_scontent_in_right_to_left, R.anim.home_scontent_out_left_to_right)
                    .replace(R.id.fragment_content_third_layout, fragment, menu.toString())
                    .commit();
            ((HomeAcitvity) mActiviyt).setCurrentThirdContentTag(menu);
        } else {
            hideHomeThirdContent();
        }
    }

    @Override
    public boolean hideHomeThirdContent() {
        AnimListenFragment fragment = (AnimListenFragment) mActiviyt.getFragmentManager()
                .findFragmentByTag(null != ((HomeAcitvity) mActiviyt).getCurrentThirdContentTag()
                        ? ((HomeAcitvity) mActiviyt).getCurrentThirdContentTag().toString() : "");
        if (null == fragment) {
            return false;
        }
        fragment.setAnimationListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mActiviyt.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mActiviyt.findViewById(R.id.fragment_content_third_layout).setVisibility(View.GONE);
                    }
                });
            }
        });

        int state = mActiviyt.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.home_scontent_in_right_to_left, R.anim.home_scontent_out_left_to_right)
                .remove(fragment)
                .commit();
        if (state < 0)
            ((HomeAcitvity) mActiviyt).setCurrentThirdContentTag(null);

        return state < 0;
    }

    private void checkScMenuState() {
        MainController.HomeMenu temp = ((HomeAcitvity) mActiviyt).getCurrentMenuTag();
        if (temp == MainController.HomeMenu.SETTING || temp == MainController.HomeMenu.PERSON_INFO) {
            hideHomeProfile();
        }
    }

    private void checkMenuState() {
        MainController.HomeMenu temp = ((HomeAcitvity) mActiviyt).getCurrentMenuTag();
        if (temp == MainController.HomeMenu.SETTING || temp == MainController.HomeMenu.PERSON_INFO) {
            hideHomeProfile();
        }

        MainController.HomeMenu tempSContent = ((HomeAcitvity) mActiviyt).getCurrentSContentTag();
        if (null != tempSContent || tempSContent == MainController.HomeMenu.CODE) {
            hideHomeSecondContent();
        }
    }

    private void showConttentItem(MainController.HomeMenu menu) {
        if (menu != (((HomeAcitvity) mActiviyt).getCurrentContentTag())) {
            AnimListenFragment fragment = (AnimListenFragment) HomeFragmentFactory.create(menu);
            if (null == fragment) {
                return;
            }
            int animIn, animOut;
            if (menu == MainController.HomeMenu.STORE_SUDOKU) {
                animIn = R.anim.home_scontent_in_right_to_left;
                animOut = R.anim.code_out_right_to_left;
            } else if (menu == MainController.HomeMenu.STORE_GALLERY
                    && MainController.HomeMenu.STORE_SUDOKU ==
                    (((HomeAcitvity) mActiviyt).getCurrentContentTag())) {
                animIn = R.anim.code_in_left_to_right;
                animOut = R.anim.code_out_left_to_right;
            } else {
                animIn = R.anim.home_menu_in_left_to_right;
                animOut = R.anim.home_menu_out_right_to_left;
            }
            mActiviyt.getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(animIn, animOut)
                    .replace(R.id.fragment_content_layout, fragment, menu.toString())
                    .commit();
            ((HomeAcitvity) mActiviyt).setCurrentContentTag(menu);
        }
    }


    @Override
    public boolean hideHomeMenu() {
        return false;
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
        if (null == shareInfoDialog) {
            shareInfoDialog = new ShareInfoDialog(mActiviyt);
        }
        shareInfoDialog.showShareItems(thunm, name, url, null);
    }

    @Override
    public void showWebViewNotify(String url, int type) {
        Intent intent = new Intent(mActiviyt, CheckTypeActivity.class);
        intent.putExtra("url", url);
        intent.putExtra("type", String.valueOf(type));
        mActiviyt.startActivity(intent);
    }


    @Override
    public void showProductInfo(ProductInfo info) {
        TourApplication.instance().getmMainController().getInnState().setProductInfo(info);
        this.showHomeSecondContent(MainController.HomeMenu.MGR_PRO_AE);
    }

    @Override
    public void showProLocalImg(Uri uri) {
        Fragment fragment = mActiviyt.getFragmentManager().findFragmentByTag(((HomeAcitvity) mActiviyt).getCurrentSContentTag().toString());
        if (null != fragment) {
            ((ProductAddEdit) fragment).showLocalImg(uri);
        }
    }

    @Override
    public void showProductAddSuccessed() {
        Fragment fragment = mActiviyt.getFragmentManager().findFragmentByTag(((HomeAcitvity) mActiviyt).getCurrentSContentTag().toString());
        if (null != fragment) {
            ((ProductAddEdit) fragment).showProductAddSuccessed();
        }
    }

    @Override
    public void showProductEditSuccessed() {
        Fragment fragment = mActiviyt.getFragmentManager().findFragmentByTag(((HomeAcitvity) mActiviyt).getCurrentSContentTag().toString());
        if (null != fragment) {
            ((ProductAddEdit) fragment).showProductEditSuccessed();
        }
    }

    @Override
    public void showProductBreGrid(List<ProductBreviary> pros) {
        MainController.HomeMenu menu = ((HomeAcitvity) mActiviyt).getCurrentSContentTag();
        Fragment fragment = mActiviyt.getFragmentManager().findFragmentByTag(null == menu ? "" : menu.toString());
        if (null != fragment) {
            ((HomeStoreFragment) fragment).showProductBre(pros);
        }
    }

    @Override
    public void showProductBreGallery(List<ProductBreviary> pros) {
        Fragment fragment = mActiviyt.getFragmentManager().findFragmentByTag(((HomeAcitvity) mActiviyt).getCurrentContentTag().toString());
        if (null != fragment) {
            ((HomeStoreFragment) fragment).showProductBre(pros);
        }
    }

    @Override
    public void showProductBre(List<ProductBreviary> pros) {
        MainController.HomeMenu menu = ((HomeAcitvity) mActiviyt).getCurrentSContentTag();
        Fragment fragment = mActiviyt.getFragmentManager().findFragmentByTag(null == menu ? "" : menu.toString());
        if (null == fragment) {
            fragment = mActiviyt.getFragmentManager().findFragmentByTag(((HomeAcitvity) mActiviyt).getCurrentContentTag().toString());
        }

        if (null != fragment) {
            ((HomeStoreFragment) fragment).showProductBre(pros);
        }
    }

    @Override
    public void showVerifyCode(MainController.HomeMenu menu, Bundle bundle) {
        AnimListenFragment fragment = (AnimListenFragment) mActiviyt.getFragmentManager().findFragmentByTag(menu.toString());
        int animIn, animOut;
        if (null == fragment) {
            fragment = (AnimListenFragment) HomeFragmentFactory.create(menu);
        }

        if (menu == MainController.HomeMenu.VERIFY_SUCCESS) {
            animIn = R.anim.code_in_right_to_left;
            animOut = R.anim.code_out_right_to_left;
        } else {
            animIn = R.anim.code_in_left_to_right;
            animOut = R.anim.code_out_left_to_right;
        }
        if (null == fragment) {
            return;
        }
        fragment.setArguments(bundle);
        mActiviyt.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(animIn, animOut)
                .replace(R.id.fragment_code_main, fragment, menu.toString())
                .commit();
    }

    @Override
    public void showSetChangePwd() {
        AnimListenFragment fragment = (AnimListenFragment) SetChangepwdFragment.create();
        mActiviyt.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.code_in_right_to_left, R.anim.code_out_left_to_right)
                .replace(R.id.fragment_code_main, fragment)
                .commit();
    }

    @Override
    public void showSettingItem(String item) {
        AnimListenFragment
                fragment = (AnimListenFragment) SettingFragmentFactory.create(item);
        if (null == fragment) {
            return;
        }
        mActiviyt.getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.code_in_right_to_left, R.anim.code_out_left_to_right)
                .replace(R.id.setting_dialog_content, fragment)
                .commit();
    }
}
