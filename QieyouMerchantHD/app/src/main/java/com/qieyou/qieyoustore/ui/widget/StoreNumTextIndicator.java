package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qieyou.qieyoustore.R;

import java.util.Objects;


/**
 * Created by zhoufeng'an on 2015/8/18.
 */
public class StoreNumTextIndicator extends AbstLinearIndicator implements View.OnClickListener {

    public StoreNumTextIndicator(Context context) {
        super(context);
    }

    public StoreNumTextIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StoreNumTextIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public View getTag(Object dataItem) {
        View tag = View.inflate(this.getContext(),R.layout.widget_store_indicator_item, null);
        ((TextView)(tag.findViewById(R.id.store_indicator_text))).setText((String) dataItem);
        return tag;
    }

    @Override
    public View initFocusUI() {
        return View.inflate(this.getContext(),R.layout.widget_store_indicator_focus,null);
    }

    @Override
    public void viewFocused(View focused) {

    }

    @Override
    public void viewUnfocused(View unfocused) {

    }

    public void updateFoucusState(Object data){
        ((TextView)focusView).setText(this.getContext().getString(R.string.store_indicator_mask_text_str,(int)data));
    }


}
