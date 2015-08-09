package com.totour.qieyoumerchanthd.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.totour.qieyoumerchanthd.util.ViewHolderHelper;
import com.totour.qieyoumerchanthd.widget.ColorTextButton;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
final class HomeNaviHolderHelper  extends ViewHolderHelper{
    public static HomeNaviHolderHelper get(Context context,int position, View convertView, ViewGroup parent, int layoutId){
        if(null == convertView){
            return new HomeNaviHolderHelper(context, parent, layoutId, position);
        }
        HomeNaviHolderHelper existingHelper = (HomeNaviHolderHelper)convertView.getTag();
        existingHelper.position = position;
        return existingHelper;
    }


    protected HomeNaviHolderHelper(Context context, ViewGroup parent, int layoutId, int position) {
        super(context, parent, layoutId, position);
    }

    public HomeNaviHolderHelper setSelected(int viewId, boolean selected){
        ((ColorTextButton)findViewById(viewId)).setSelected(selected);
        return this;
    }
}
