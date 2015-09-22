package com.qieyou.qieyoustore.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.UserController;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.util.CountDownTimer;
import com.qieyou.qieyoustore.util.ToastUtil;
import com.qieyou.qieyoustore.util.TourRegularUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class SetChangepwdFragment extends AnimListenFragment implements UserController.SetChangePwdUi, View.OnClickListener,
        TextWatcher, CountDownTimer.CountListener {
    private UserController.SetChangePwdCallbacks mUserLoginFindPwdCallbacks;
    private EditText login_findpwd_new;
    private EditText login_findpwd_vericode;
    private TextView login_findpwd_get_vericode;
    private Button login_findpwd_submit;
    CountDownTimer countDoun;

    public SetChangepwdFragment() {
    }


    public static SetChangepwdFragment create() {
        SetChangepwdFragment fragment = new SetChangepwdFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_pwd, null);



        login_findpwd_vericode = (EditText) view.findViewById(R.id.verfy_code);
        login_findpwd_new = (EditText) view.findViewById(R.id.change_new_pwd);
        login_findpwd_get_vericode = (TextView) view.findViewById(R.id.change_get_pwd);
        login_findpwd_submit = (Button) view.findViewById(R.id.change_submit_new);

        login_findpwd_vericode.addTextChangedListener(this);
        login_findpwd_new.addTextChangedListener(this);

        login_findpwd_get_vericode.setFocusable(true);
        login_findpwd_get_vericode.setFocusableInTouchMode(true);
        login_findpwd_get_vericode.requestFocus();

        login_findpwd_get_vericode.setOnClickListener(this);
        login_findpwd_submit.setOnClickListener(this);
        return view;
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

    UserController getController() {
        return MerchanthdApplication.instance().getmMainController().getmUserController();
    }


    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
        mUserLoginFindPwdCallbacks=  (UserController.SetChangePwdCallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.change_get_pwd == v.getId()) {
            mUserLoginFindPwdCallbacks.getVeriCode(TourApplication.instance().getDaoUser().getUserMobile());

        } else if (R.id.change_submit_new == v.getId()) {
            mUserLoginFindPwdCallbacks.changePassword(TourApplication.instance().getDaoUser().getUserMobile(),
                    login_findpwd_vericode.getText().toString(),
                    login_findpwd_new.getText().toString());

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (login_findpwd_submit.isClickable() && !TourRegularUtil.checkPasswordCanChanged(TourApplication.instance().getDaoUser().getUserMobile(),
                login_findpwd_vericode.getText().toString(),
                login_findpwd_new.getText().toString())) {
            setLoginButtonState(false);
        } else if (!login_findpwd_submit.isClickable() && TourRegularUtil.checkPasswordCanChanged(TourApplication.instance().getDaoUser().getUserMobile(),
                login_findpwd_vericode.getText().toString(),
                login_findpwd_new.getText().toString())) {
            setLoginButtonState(true);
        }
    }

    private void setLoginButtonState(boolean able) {
        login_findpwd_submit.setClickable(able);
        login_findpwd_submit.setBackgroundResource(able ? R.drawable.login_corners_bg : R.drawable.store_vp_bottom_unable_bg);
    }

    public void onDestroy(){
        if(null != countDoun){
            countDoun.cancel();
        }
        super.onDestroy();
    }

    @Override
    public void passwordChanged(String pwd) {
        TourApplication.instance().getDaoUser().setPassword(pwd);
        ToastUtil.showShortToast("密码修改成功");
    }

    @Override
    public void showCuntDown() {
        if (null != countDoun) {
            countDoun.cancel();
        }

        countDoun = new CountDownTimer(this.getActivity(),this);
        countDoun.schedule(0,1000);
    }

    @Override
    public void refreshCount(int leftTimes) {
        if (0 <= leftTimes) {
            login_findpwd_get_vericode.setText(SetChangepwdFragment.this.getString(R.string.login_findpwd_get_code_params, leftTimes));
            login_findpwd_get_vericode.setClickable(false);
        } else {
            login_findpwd_get_vericode.setText(R.string.login_findpwd_get_code);
            login_findpwd_get_vericode.setClickable(true);
        }
    }
}
