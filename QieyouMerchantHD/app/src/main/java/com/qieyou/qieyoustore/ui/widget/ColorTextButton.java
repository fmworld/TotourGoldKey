package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.qieyou.qieyoustore.R;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ColorTextButton extends TextView {
    private int selectedTextColor ;
    private int unselectedTextColor;
    public ColorTextButton(Context context) {
        super(context);
        initAttrs(null);
    }

    public ColorTextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public ColorTextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        if(null == attrs){
            selectedTextColor =getResources().getColor(R.color.home_navogation_item_selected_title);
            unselectedTextColor =getResources().getColor(R.color.home_navogation_item_unselected_title);
        }else{
            TypedArray a = this.getContext().obtainStyledAttributes(attrs, R.styleable.ColorTextButton);
            selectedTextColor = a.getColor(R.styleable.ColorTextButton_selectedTextColor,this.getResources().getColor(R.color.home_navogation_item_selected_title));
            unselectedTextColor = a.getColor(R.styleable.ColorTextButton_unselectedTextColor,this.getResources().getColor(R.color.home_navogation_item_unselected_title));
            a.recycle();
        }
    }

    public void setSelected(boolean selected){
        super.setSelected(selected);
        this.setTextColor(selected?selectedTextColor:unselectedTextColor);
    }

    public void setSelectedTextColor(int selectedTextColor) {
        this.selectedTextColor = selectedTextColor;
    }

    public void setUnselectedTextColor(int unselectedTextColor) {
        this.unselectedTextColor = unselectedTextColor;
    }
}
