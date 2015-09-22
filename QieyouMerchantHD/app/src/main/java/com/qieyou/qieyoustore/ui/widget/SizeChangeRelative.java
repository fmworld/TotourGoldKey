package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

import com.fm.fmlib.utils.DisplayUtil;

/**
 * Created by zhoufeng'an on 2015/9/16.
 */
public class SizeChangeRelative extends RelativeLayout {
    private SoftInputListener softInputListener;
    private int heightDevider;
    public interface SoftInputListener{
        void softInputShowed();
        void softInputHide();
    }

    public SizeChangeRelative(Context context) {
        super(context);
        initData();
    }

    public SizeChangeRelative(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public SizeChangeRelative(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData(){
        heightDevider = DisplayUtil.getScreenHeight(getContext())*3/4;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if(null != softInputListener){
            if(h > heightDevider){
                softInputListener.softInputHide();
            }else{
                softInputListener.softInputShowed();
            }
        }
        super.onSizeChanged(w, h, oldw, oldw);
        Log.v("resizechange", "h  old " + oldh);
        Log.v("resizechange", "h  new " + h);
        Log.v("resizechange", "h  d " + heightDevider);

    }

    public void setSoftInputListener(SoftInputListener softInputListener) {
        this.softInputListener = softInputListener;
    }
}
