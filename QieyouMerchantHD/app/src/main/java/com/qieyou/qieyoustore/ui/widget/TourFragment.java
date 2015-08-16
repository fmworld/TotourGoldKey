package com.qieyou.qieyoustore.ui.widget;

import android.app.Fragment;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public abstract class TourFragment extends Fragment {
    boolean firstResume = true;
    @Override
    public void onResume() {
        super.onResume();
        if(firstResume){
            onFirstResume();
            firstResume = false;
        }
    }
    public abstract void onFirstResume();
}
