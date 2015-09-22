package com.qieyou.qieyoustore.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fm.fmlib.controllers.UserController;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.SizeChangeRelative;
import com.qieyou.qieyoustore.util.CountDownTimer;
import com.qieyou.qieyoustore.util.TourRegularUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class LoginFindpwdFragment extends Fragment implements UserController.UserFindPwdUi, View.OnClickListener, TextWatcher, CountDownTimer.CountListener {
    private UserController.UserLoginFindPwdCallbacks mUserLoginFindPwdCallbacks;
    private EditText login_findpwd_account;
    private EditText login_findpwd_new;
    private EditText login_findpwd_vericode;
    private Button login_findpwd_get_vericode;
    private Button login_findpwd_submit;
    CountDownTimer countDownTimer;
    private Handler handler = new Handler();

    public LoginFindpwdFragment() {
    }


    public static LoginFindpwdFragment create() {
        LoginFindpwdFragment fragment = new LoginFindpwdFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_login_findpwd, null);
        login_findpwd_account = (EditText) view.findViewById(R.id.login_findpwd_account);
        login_findpwd_vericode = (EditText) view.findViewById(R.id.login_findpwd_vericode);
        login_findpwd_new = (EditText) view.findViewById(R.id.login_findpwd_new);
        login_findpwd_get_vericode = (Button) view.findViewById(R.id.login_findpwd_get_vericode);
        login_findpwd_submit = (Button) view.findViewById(R.id.login_findpwd_submit);
        ((SizeChangeRelative)view).setSoftInputListener(new SizeChangeRelative.SoftInputListener() {
            @Override
            public void softInputShowed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.findViewById(R.id.login_findpwd_code_notify).setVisibility(View.GONE);
                        view.findViewById(R.id.login_phone_number).setVisibility(View.GONE);
                    }
                });
            }

            @Override
            public void softInputHide() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        view.findViewById(R.id.login_findpwd_code_notify).setVisibility(View.VISIBLE);
                        view.findViewById(R.id.login_phone_number).setVisibility(View.VISIBLE);
                    }
                });
            }
        });
        login_findpwd_account.addTextChangedListener(this);
        login_findpwd_vericode.addTextChangedListener(this);
        login_findpwd_new.addTextChangedListener(this);

        login_findpwd_get_vericode.setOnClickListener(this);
        login_findpwd_submit.setOnClickListener(this);
        view.findViewById(R.id.login_findpwd_back).setOnClickListener(this);
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
        mUserLoginFindPwdCallbacks = (UserController.UserLoginFindPwdCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.login_findpwd_get_vericode == v.getId()) {
            mUserLoginFindPwdCallbacks.getVeriCode(login_findpwd_account.getText().toString());

        } else if (R.id.login_findpwd_submit == v.getId()) {
            mUserLoginFindPwdCallbacks.changePassword(login_findpwd_account.getText().toString(),
                    login_findpwd_vericode.getText().toString(),
                    login_findpwd_new.getText().toString());

        } else if (R.id.login_findpwd_back == v.getId()) {
            ((BaseTourActivity) this.getActivity()).getDisplay().showLogin();

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
        if (login_findpwd_submit.isClickable() && !TourRegularUtil.checkPasswordCanChanged(login_findpwd_account.getText().toString(),
                login_findpwd_vericode.getText().toString(),
                login_findpwd_new.getText().toString())) {
            setLoginButtonState(false);
        } else if (!login_findpwd_submit.isClickable() && TourRegularUtil.checkPasswordCanChanged(login_findpwd_account.getText().toString(),
                login_findpwd_vericode.getText().toString(),
                login_findpwd_new.getText().toString())) {
            setLoginButtonState(true);
        }
    }

    private void setLoginButtonState(boolean able) {
        login_findpwd_submit.setClickable(able);
        login_findpwd_submit.setBackgroundResource(able ? R.drawable.login_corners_bg
                : R.drawable.login_unclickabe_corners_bg);
    }

    public void showGetVericodeCountDown() {
        Log.v("tour0888", "showGetVericodeCountDown");
        if (null != countDownTimer) {
            countDownTimer.cancel();
        }
        countDownTimer = new CountDownTimer(this.getActivity(),this);
        countDownTimer.schedule(0, 1000);
    }

    public void onDestroy(){
        if(null != countDownTimer){
            countDownTimer.cancel();
        }
        super.onDestroy();
    }

    private void updateCountDoun(int leftTimes){
        if (0 <= leftTimes) {
            login_findpwd_get_vericode.setText(LoginFindpwdFragment.this.getString(R.string.login_findpwd_get_code_params, leftTimes));
            login_findpwd_get_vericode.setClickable(false);
        } else {
            login_findpwd_get_vericode.setText(R.string.login_findpwd_get_code);
            login_findpwd_get_vericode.setClickable(true);
        }
    }

    @Override
    public void refreshCount(int leftTimes) {
        updateCountDoun(leftTimes);
    }
}
