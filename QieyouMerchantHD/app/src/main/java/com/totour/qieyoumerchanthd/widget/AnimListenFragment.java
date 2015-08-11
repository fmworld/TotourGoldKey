package com.totour.qieyoumerchanthd.widget;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.util.Log;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class AnimListenFragment extends Fragment {
    private boolean showing = false;
    private AnimatorListenerAdapter listener;

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {

        final Animator anim = AnimatorInflater.loadAnimator(getActivity(), nextAnim);
        if(null != listener){
            anim.addListener(listener);
        }

        return anim;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setAnimationListener(AnimatorListenerAdapter _lisnter) {
        this.listener = _lisnter;
    }

}
