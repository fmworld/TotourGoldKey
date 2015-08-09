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
    private boolean showedIn = false;
    private AnimatorListenerAdapter listener;

    @Override
    public Animator onCreateAnimator(int transit, boolean enter, int nextAnim) {

        final Animator anim = AnimatorInflater.loadAnimator(getActivity(), nextAnim);
        if(null != listener){
            anim.addListener(listener);
        }

        return anim;
    }

    public void setShowedIn(boolean showedIn) {
        this.showedIn = showedIn;
    }

    public void setAnimationListener(AnimatorListenerAdapter _lisnter) {
        this.listener = _lisnter;
    }

}
