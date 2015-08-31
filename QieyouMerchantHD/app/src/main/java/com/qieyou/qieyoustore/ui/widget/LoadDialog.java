package com.qieyou.qieyoustore.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.qieyou.qieyoustore.R;


/**
 * Created by zhoufeng'an on 2015/8/30.
 */
public class LoadDialog extends Dialog{
    public LoadDialog(Context context) {
        super(context, R.style.translucent);
        initUI();
    }

    public LoadDialog(Context context, int theme) {
        super(context, R.style.load_translucent);
        initUI();
    }

    private void initUI(){
        RelativeLayout view = (RelativeLayout)View.inflate(this.getContext(),R.layout.widget_loading,null);
        this.setContentView(view);
    }
}
