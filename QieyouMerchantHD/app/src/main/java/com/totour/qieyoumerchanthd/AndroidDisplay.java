package com.totour.qieyoumerchanthd;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;

import com.fm.fmlib.Display;
import com.totour.qieyoumerchanthd.fragment.LoginFindpwdFragment;
import com.totour.qieyoumerchanthd.fragment.LoginInFragment;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class AndroidDisplay implements Display {
    private Activity mActiviyt;

    public AndroidDisplay(Activity mActivyt){
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
                .replace(R.id.fragment_main, fragment,fragment.getClass().getSimpleName())
                .commit();
    }

    @Override
    public void showHomePage() {
        mActiviyt.startActivity(new Intent(mActiviyt, MainAcitvity.class));
    }

    @Override
    public void showVeriCodeCountdown() {
        ((LoginActivity)mActiviyt).showCodeCountDown();
    }
}
