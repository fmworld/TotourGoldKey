package com.qieyou.qieyoustore.ui.fragment;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.UserController;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.util.ToastUtil;
import com.qieyou.qieyoustore.util.TourRegularUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class SetAboutFragment extends AnimListenFragment  {

    public SetAboutFragment() {
    }


    public static SetAboutFragment create() {
        SetAboutFragment fragment = new SetAboutFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, null);


        return view;
    }
}
