package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.qieyou.qieyoustore.ui.widget.ColorTextButton;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
final class HomeMgrProPicsHolderHelper extends ViewHolderHelper{
    public static HomeMgrProPicsHolderHelper get(Context context,int position, View convertView, ViewGroup parent, int layoutId){
        if(null == convertView){
            return new HomeMgrProPicsHolderHelper(context, parent, layoutId, position);
        }
        HomeMgrProPicsHolderHelper existingHelper = (HomeMgrProPicsHolderHelper)convertView.getTag();
        existingHelper.position = position;
        return existingHelper;
    }


    protected HomeMgrProPicsHolderHelper(Context context, ViewGroup parent, int layoutId, int position) {
        super(context, parent, layoutId, position);
    }

    public HomeMgrProPicsHolderHelper setPlaceHoldImg(int viewId, int res){
        ((SimpleDraweeView)findViewById(viewId)).getHierarchy().setPlaceholderImage(res);
        return this;
    }

    public HomeMgrProPicsHolderHelper setPlaceHoldImg(int viewId, Uri uri){
        ((SimpleDraweeView)findViewById(viewId)).setImageURI(uri);
        return this;
    }
}
