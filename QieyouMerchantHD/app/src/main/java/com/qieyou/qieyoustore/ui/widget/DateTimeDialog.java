package com.qieyou.qieyoustore.ui.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.DateUtil;

import java.util.Date;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class DateTimeDialog extends Dialog {
    private Date current;
    private WheelDatePick pick;
    private TextView targetView;

    public DateTimeDialog(Context context) {
        super(context);
        initView();
    }

    public DateTimeDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    protected DateTimeDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        pick = new WheelDatePick(this.getContext());
        ActionBar.LayoutParams prams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        prams.gravity = Gravity.CENTER;
        addContentView(pick.getContent(), prams);
        Log.v("show date", "onClick");
        pick.setClickListener(new WheelDatePick.WheelDatePickClickListener() {
            @Override
            public void navigationClicked() {
                dismiss();
            }

            @Override
            public void positiveClicked(Date date) {
                Log.v("showdate", "date " + date.toString());
                current = date;
                if(null != targetView){
                    targetView.setText(DateUtil.formatDateTime(date));
                }
                dismiss();
            }
        });
    }

    public Date getCurrent() {
        return current;
    }

    public TextView getTargetView() {
        return targetView;
    }

    public void setTargetView(TextView targetView) {
        this.targetView = targetView;
    }
}
