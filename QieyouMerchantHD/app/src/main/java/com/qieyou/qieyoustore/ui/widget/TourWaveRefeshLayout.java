package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by zhoufeng'an on 2015/8/22.
 */
public class TourWaveRefeshLayout extends WaveSwipeRefreshLayout {
    public TourWaveRefeshLayout(Context context) {
        super(context);
    }

    public TourWaveRefeshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TourWaveRefeshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void ensureTarget() {
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child instanceof PullToRefreshListView) {
                    mTarget = child;
                    break;
                }
            }
        }

        if (mTarget == null) {
            throw new IllegalStateException("This view must have at least one AbsListView");
        }
    }

    public boolean canChildScrollUp() {
        if (mTarget == null) {
            return false;
        }

        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof PullToRefreshListView) {
                final AbsListView absListView = (AbsListView) ((PullToRefreshListView)mTarget).getRefreshableView();
                return absListView.getChildCount() > 0 && (absListView.getFirstVisiblePosition() > 0
                        || absListView.getChildAt(0).getTop() < absListView.getPaddingTop());
            } else {
                return ((PullToRefreshListView)mTarget).getRefreshableView().getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(((PullToRefreshListView)mTarget).getRefreshableView(), -1);
        }
    }

//    protected boolean chargeNeedBeginRefresh(float yDiff){
//        super.chargeNeedBeginRefresh(yDiff);
//
////        return yDiff > ViewConfiguration.get(getContext()).getScaledTouchSlop() && !isRefreshing();
//    }
}
