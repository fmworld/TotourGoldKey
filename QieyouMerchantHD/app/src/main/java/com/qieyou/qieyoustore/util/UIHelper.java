package com.qieyou.qieyoustore.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhoufeng'an on 2015/9/18.
 */
public class UIHelper {
    public static void setViewHeight(RecyclerView lv) {
        RecyclerView.Adapter la = lv.getAdapter();
        if(null == la) {
            return;
        }
        // calculate height of all items.
        int h = 0;
        final int cnt = la.getItemCount();
        for(int i=0; i<cnt; i++) {
            View item = lv.getChildAt(i);
            if(null != item){
                item.measure(0, 0);
                h += item.getMeasuredHeight();
            }

        }
        // reset ListView height
        ViewGroup.LayoutParams lp = lv.getLayoutParams();
//        lp.height = h + (lv. * (cnt - 1));
        lp.height = 500;
        lv.setLayoutParams(lp);
    }
}
