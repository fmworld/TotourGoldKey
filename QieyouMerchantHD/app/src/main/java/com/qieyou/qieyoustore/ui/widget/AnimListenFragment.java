package com.qieyou.qieyoustore.ui.widget;

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
        try {
            final Animator anim = AnimatorInflater.loadAnimator(getActivity(), nextAnim);
            if (null != listener) {
                anim.addListener(listener);
            }
            return anim;
        } catch (Exception e) {
            Log.v("AnimListenFragment", "e "+e.getMessage());
        }

        return null;

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

    @Override
    public void onDestroy() {
        setAnimationListener(null);
        super.onDestroy();
    }
}
