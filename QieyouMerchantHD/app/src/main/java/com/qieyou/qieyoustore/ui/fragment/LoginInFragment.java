package com.qieyou.qieyoustore.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fm.fmlib.controllers.UserController;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.LoginActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.TourRegularUtil;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class LoginInFragment extends Fragment implements UserController.UserLoginUi, View.OnClickListener, TextWatcher {
    private UserController.UserLoginUiCallbacks mUserLoginUiCallbacks;
    private EditText login_account;
    private EditText login_pwd;
    private Button login_loginIn;

    public static LoginInFragment create() {
        LoginInFragment fragment = new LoginInFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_in, null);
        login_account = (EditText) view.findViewById(R.id.login_account);
        login_pwd = (EditText) view.findViewById(R.id.login_pwd);
        login_loginIn = (Button) view.findViewById(R.id.login_loginIn);
        login_account.addTextChangedListener(this);
        login_pwd.addTextChangedListener(this);
        login_loginIn.setOnClickListener(this);
        view.findViewById(R.id.login_find_pwd).setOnClickListener(this);
        view.findViewById(R.id.login_find_pwd).setFocusable(true);
        view.findViewById(R.id.login_find_pwd).setFocusableInTouchMode(true);
        view.findViewById(R.id.login_find_pwd).requestFocus();
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
    public void logined() {
        ((LoginActivity) this.getActivity()).getDisplay().showHomePage();
        this.getActivity().finish();
    }

    @Override
    public void loginFinished() {
        setLoginButtonState(true);
        login_loginIn.setText(this.getString(R.string.login_begin));
    }

    @Override
    public void setCallbacks(UserController.UserUiCallbacks callbacks) {
        mUserLoginUiCallbacks = (UserController.UserLoginUiCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.login_find_pwd == v.getId()) {
            ((BaseTourActivity) this.getActivity()).getDisplay().showFindPassword();
        } else if (R.id.login_loginIn == v.getId()) {
            setLoginButtonState(false);
            login_loginIn.setText(this.getString(R.string.login_loging));
            mUserLoginUiCallbacks.loginIn(login_account.getText().toString(), login_pwd.getText().toString());
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
        if (login_loginIn.isClickable() && !TourRegularUtil.checkLoginable(login_account.getText().toString(), login_pwd.getText().toString())) {
            setLoginButtonState(false);
        } else if (!login_loginIn.isClickable()&&TourRegularUtil.checkLoginable(login_account.getText().toString(), login_pwd.getText().toString())) {
            setLoginButtonState(true);
        }
    }

    private void setLoginButtonState(boolean able){
        login_loginIn.setClickable(able);
        login_loginIn.setBackgroundResource(able?R.drawable.login_corners_bg:R.drawable.login_unclickabe_corners_bg);
    }
}
