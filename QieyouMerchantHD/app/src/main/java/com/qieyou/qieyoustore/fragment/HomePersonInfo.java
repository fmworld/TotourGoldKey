package com.qieyou.qieyoustore.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.dao.Inn;
import com.fm.fmlib.network.TourConfig;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.widget.AnimListenFragment;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomePersonInfo extends AnimListenFragment implements InnController.InnStoreUi, View.OnClickListener {
    private InnController.InnUiCallbacks mInnUiCallbacks;
    private EditText login_account;
    private EditText login_pwd;
    private Button login_loginIn;
    private View content;
    public static HomePersonInfo create() {
        HomePersonInfo fragment = new HomePersonInfo();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_home_person, null);
        initData(content);
//        home_person_user_icon.setImageURI(Uri.parse("http://img1.fjtv.net/material/news/img/2015/07/4b9db2f97de68cbf05df2517be05db1a.jpg"));
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

    InnController getController() {
        return MerchanthdApplication.instance().getmMainController().getInnController();
    }


    @Override
    public void setCallbacks(InnController.InnUiCallbacks callbacks) {
        mInnUiCallbacks = (InnController.InnStoreUiCallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void initView() {
        initData(content);
    }

    @Override
    public void onClick(View v) {
    }

    private void initData(View view){
        view.findViewById(R.id.home_psn_serve_phone).setOnClickListener(this);
        view.setOnClickListener(this);
        Inn inn = TourApplication.instance().getDaoInn();
        ((TextView)view.findViewById(R.id.home_person_usr_name)).setText(inn.getInnerContact());
        ((TextView)view.findViewById(R.id.home_psn_user_phone)).setText(inn.getInnerMoblie());
        ((TextView)view.findViewById(R.id.home_psn_user_phone)).setText(inn.getInnerMoblie());

        ((TextView)view.findViewById(R.id.home_psn_store_name)).setText(inn.getInnName());
        ((TextView)view.findViewById(R.id.home_psn_pro_num)).setText(this.getString(R.string.home_person_pro_str,inn.getInnProducts()));
        ((TextView)view.findViewById(R.id.home_psn_serv_total)).setText(this.getString(R.string.home_person_serv_total_str,inn.getInnerServe()));
        ((TextView)view.findViewById(R.id.home_psn_serv_today)).setText(this.getString(R.string.home_person_serv_today_str,inn.getInnerTodayServe()));

        ((SimpleDraweeView)view.findViewById(R.id.home_psn_store_icon)).setImageURI(Uri.parse(TourConfig.instance().getImageRoot() + "/" + inn.getInnHead()));
        ((SimpleDraweeView)view.findViewById(R.id.home_person_user_icon)).setImageURI(Uri.parse(TourConfig.instance().getImageRoot()+"/"+inn.getInnerHead()));
        initScore(view, inn.getInnerScore());
    }

    private void initScore(View view, String score){
        ((TextView)view.findViewById(R.id.home_psn_score_value)).setText(score);
    }
}
