package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.R;

/**
 * Created by zhoufeng'an on 2015/8/31.
 */
public class StoreItemEmptyView extends LinearLayout implements View.OnClickListener {
    private TextView goMall;
    private TextView addPro;
    private TextView notifyText;

    public StoreItemEmptyView(Context context) {
        super(context);
        initView();
    }

    public StoreItemEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public StoreItemEmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        this.setGravity(Gravity.CENTER);
        this.setOrientation(LinearLayout.VERTICAL);
        LayoutParams params_notify = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params_notify.bottomMargin = DisplayUtil.dip2px(this.getContext(), 15);
        notifyText = new TextView(this.getContext());
        notifyText.setText(this.getContext().getString(R.string.store_item_empty));
        notifyText.setLayoutParams(params_notify);
        notifyText.setTextSize(14);
        this.addView(notifyText);

        LinearLayout butons = new LinearLayout(this.getContext());
        LayoutParams params_gomall = new LayoutParams(DisplayUtil.dip2px(this.getContext(), 200), DisplayUtil.dip2px(this.getContext(), 58));
        goMall = new TextView(this.getContext());
        goMall.setText(this.getContext().getString(R.string.store_go_mall));
        goMall.setTextColor(getResources().getColor(R.color.white));
        goMall.setBackgroundResource(R.drawable.bg_coners_oringe_round);
        goMall.setTextSize(14);
        goMall.setGravity(Gravity.CENTER);
        goMall.setLayoutParams(params_gomall);
        goMall.setOnClickListener(this);

        butons.addView(goMall);

        LayoutParams params_addpro = new LayoutParams(DisplayUtil.dip2px(this.getContext(), 200), DisplayUtil.dip2px(this.getContext(), 58));
        params_addpro.leftMargin = DisplayUtil.dip2px(this.getContext(), 15);
        addPro = new TextView(this.getContext());
        addPro.setText(this.getContext().getString(R.string.store_add_product));
        addPro.setBackgroundResource(R.drawable.bg_coners_green_round);
        addPro.setGravity(Gravity.CENTER);
        addPro.setLayoutParams(params_addpro);
        addPro.setTextSize(14);
        addPro.setTextColor(getResources().getColor(R.color.white));
        addPro.setOnClickListener(this);

        butons.addView(addPro);

        LayoutParams params_buttons = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params_buttons.gravity = Gravity.CENTER;

        this.addView(butons, params_buttons);


    }

    public void setNotifyColor(int color){
        notifyText.setTextColor(color);
    }

    @Override
    public void onClick(View v) {
        if(v == addPro){
            ((BaseTourActivity)this.getContext()).getDisplay().showHomeSecondContent(MainController.HomeMenu.MGR_PRO_AE);
            return;
        }

        if(v == goMall){
            ((BaseTourActivity)this.getContext()).getDisplay().showHomeMenuItem(MainController.HomeMenu.MALL);
            return;
        }
    }
}
