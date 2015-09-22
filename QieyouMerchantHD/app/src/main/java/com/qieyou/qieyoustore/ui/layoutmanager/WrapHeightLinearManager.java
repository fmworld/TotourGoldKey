package com.qieyou.qieyoustore.ui.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by zhoufeng'an on 2015/9/18.
 */
public class WrapHeightLinearManager extends LinearLayoutManager {
    int widthSize = 0;

    public WrapHeightLinearManager(Context context) {
        super(context);
    }

    public WrapHeightLinearManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {
        View view;
        int measuredHeight = 0;
        int size = state.getItemCount();
        for (int i = 0; i < size; i++) {
            view = recycler.getViewForPosition(i);
            if (view != null) {
                view.measure(widthSpec, heightSpec);
                widthSize = view.getMeasuredWidth();
                measuredHeight += view.getMeasuredHeight();
            }
        }
        setMeasuredDimension(widthSize, measuredHeight);
    }
}
