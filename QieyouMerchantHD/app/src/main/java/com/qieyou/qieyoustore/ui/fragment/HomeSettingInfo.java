package com.qieyou.qieyoustore.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.UserController;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.state.UserState;
import com.fm.fmlib.tour.TourConfig;
import com.fm.fmlib.utils.DataCleanManager;
import com.qieyou.qieyoustore.HomeAcitvity;
import com.qieyou.qieyoustore.LaunchActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.SettingActivity;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.ui.widget.SlideSwitch;
import com.fm.fmlib.utils.AlertDialogUtil;
import com.fm.fmlib.utils.PackageInfoUtl;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeSettingInfo extends AnimListenFragment implements UserController.UserSettingUi, View.OnClickListener {
    private UserController.SettingUiCallbacks mSettingUiCallbacks;
    private View content;

    public static HomeSettingInfo create() {
        HomeSettingInfo fragment = new HomeSettingInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_home_setting, null);
        initView();
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    public void onDestroy(){
        super.onDestroy();
        ((HomeAcitvity)getActivity()).selectCurrentNavigationItem();
    }

    UserController getController() {
        return MerchanthdApplication.instance().getmMainController().getmUserController();
    }

    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
        mSettingUiCallbacks = (UserController.SettingUiCallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if(R.id.home_setting_tour == v.getId()){
            AlertDialogUtil.showTourPhoneDialog(this.getActivity(),this.getString(R.string.tour_server_phone));
        }else  if(R.id.home_setting_about == v.getId()){
            Intent intent = new Intent(this.getActivity(), SettingActivity.class);
            intent.putExtra("type", UserState.Setting.about.toString());
            this.getActivity().startActivity(intent);
        }else if(R.id.home_setting_chenge_pwd == v.getId()){
            Intent intent = new Intent(this.getActivity(), SettingActivity.class);
            intent.putExtra("type", UserState.Setting.changepwd.toString());
            this.getActivity().startActivity(intent);
        }else if(R.id.home_setting_feedback == v.getId()){
            Intent intent = new Intent(this.getActivity(), SettingActivity.class);
            intent.putExtra("type", UserState.Setting.feedback.toString());
            this.getActivity().startActivity(intent);
        }else if(R.id.home_setting_clear_cache == v.getId()){
            AlertDialogUtil.showAlertDialog(this.getActivity(), this.getString(R.string.cache_clean_notify),
                    new
                    DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataCleanManager.deleteFolderFile(TourConfig.instance().getPicCache().getPath(),false);
                            DataCleanManager.deleteFolderFile(TourConfig.instance().getDownloadCache().getPath(),false);
                            initCacheSize();
                        }
                    });
        }else if(R.id.setting_loginout == v.getId()){
            AlertDialogUtil.showAlertDialog(this.getActivity(), this.getString(R.string.setting_loginout_notify),
                    new
                            DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mSettingUiCallbacks.loginOut();
                                }
                            });
        }
    }

    @Override
    public void initView() {
        ((TextView) content.findViewById(R.id.home_setting_phone))
                .setText(TourApplication.instance().getDaoUser().getUserMobile());
        ((TextView) content.findViewById(R.id.home_setting_user_name))
                .setText(TourApplication.instance().getDaoInn().getInnerContact());
        ((SimpleDraweeView) content.findViewById(R.id.home_setting_user_icon))
                .setImageURI(Uri.parse(TourConfig.instance().getImageRoot() + "/"
                        + TourApplication.instance()
                        .getDaoUser().getInnerHead()));
        (content.findViewById(R.id.home_setting_tour)).setOnClickListener(this);
        (content.findViewById(R.id.home_setting_about)).setOnClickListener(this);
        (content.findViewById(R.id.home_setting_chenge_pwd)).setOnClickListener(this);
        (content.findViewById(R.id.home_setting_feedback)).setOnClickListener(this);
        (content.findViewById(R.id.home_setting_clear_cache)).setOnClickListener(this);
        (content.findViewById(R.id.setting_loginout)).setOnClickListener(this);
        ((SlideSwitch)content.findViewById(R.id.setting_tip_switch))
                .setState("1".equals(TourApplication.instance()
                        .getDaoProperty().getValue(ProductState.Tip)));

        ((SlideSwitch)content.findViewById(R.id.setting_tip_switch))
                .setSlideListener(new SlideSwitch.SlideListener() {
                    @Override
                    public void onOpen() {
                        TourApplication.instance()
                                .getmBus()
                                .post(new ProductState.ProductTipStateChangeEvent("1"));
                    }

                    @Override
                    public void onClose() {
                        TourApplication.instance()
                                .getmBus()
                                .post(new ProductState.ProductTipStateChangeEvent("0"));
                    }
                });

        ((TextView) content.findViewById(R.id.setting_version_value))
                .setText(this.getString(R.string.home_setting_version_str,
                        PackageInfoUtl.getVersionName(this.getActivity())));

        initCacheSize();



    }

    private void initCacheSize(){
        try {
            ((TextView)content.findViewById(R.id.setting_cache_value))
                    .setText(DataCleanManager.getFormatSize(DataCleanManager.getFolderSize(
                            TourConfig.instance().getPicCache()) + DataCleanManager.getFolderSize(TourConfig.instance().getDownloadCache())));
        }catch(Exception e){

        }
    }

    @Override
    public void loginOut() {
        Log.v("loginout", "loginOut  ");
        this.startActivity(new Intent(this.getActivity(), LaunchActivity.class));
        this.getActivity().finish();
    }
}
