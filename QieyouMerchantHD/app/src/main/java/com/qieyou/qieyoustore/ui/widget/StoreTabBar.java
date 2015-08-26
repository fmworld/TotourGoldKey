package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/18.
 */
public class StoreTabBar extends RelativeLayout implements View.OnClickListener {

    private List<ProductTag> tagsData;
    private int focusWidth;
    private int colorFocusedText;
    private int colorUnfocusedText;
    private float textSize;

    private View focusView;
    private AbstLinearIndicator.LinearIndicatorListener mLinearIndicatorListener;

    private List<TextView> tags;
    private TextView selectedView;
    private LinearLayout tagsLayout;


    public StoreTabBar(Context context) {
        super(context);
        init(null);
    }

    public StoreTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public StoreTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (null != attrs) {
            TypedArray a = this.getContext().obtainStyledAttributes(attrs,
                    R.styleable.StoreTabBar);
            focusWidth = a.getInt(R.styleable.StoreTabBar_focusWidth,
                    80);
            focusWidth = DisplayUtil.dip2px(this.getContext(), focusWidth);
            colorFocusedText = a.getColor(R.styleable.StoreTabBar_focusWidth,
                    this.getContext().getResources().getColor(R.color.store_tab_bar_focus_text));
            colorUnfocusedText = a.getColor(R.styleable.StoreTabBar_focusWidth,
                    this.getContext().getResources().getColor(R.color.store_tab_bar_unfocus_text));
            textSize = a.getDimension(R.styleable.StoreTabBar_textSize, 14);
            a.recycle();
        }


        tags = new ArrayList<>();
        tagsData = new ArrayList<>();
        initView();
    }

    private void initView() {
        initTagsLayout();
    }

    private void initTagsLayout() {
        if (null == tagsLayout) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            tagsLayout = new LinearLayout(this.getContext());
            tagsLayout.setLayoutParams(params);
            this.addView(tagsLayout, 0);
        }

        tags.clear();
        tagsLayout.removeAllViews();

        for (ProductTag item : tagsData) {
            initTag(item.getTag_name(), item.getItem_seq());
        }

        initTagsClicklistener();
        if (0 < tags.size()) {
            tags.get(0).callOnClick();
        }
        initFocus();
    }

    private void initTag(String tag_name, String item_seq) {
        if (null != tagsLayout) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(focusWidth, LayoutParams.MATCH_PARENT);
            TextView item = new TextView(this.getContext());
            item.setTextColor(colorUnfocusedText);
            item.setTextSize(textSize);
            item.setText(tag_name);
            item.setTag(item_seq);
            item.setGravity(Gravity.CENTER);
            item.setLayoutParams(params);
            tagsLayout.addView(item);
            tags.add(item);
        }
    }

    private void initFocus() {
        if (null == focusView) {
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(focusWidth, LayoutParams.MATCH_PARENT);
            params.topMargin = DisplayUtil.dip2px(this.getContext(), 5);
            params.bottomMargin = DisplayUtil.dip2px(this.getContext(), 5);
            focusView = new View(this.getContext());
            focusView.setLayoutParams(params);
            focusView.setBackgroundResource(R.drawable.home_store_tab_bar_foucus_bg);
            focusView.setVisibility(View.INVISIBLE);
            this.addView(focusView, 0);
        }

        focusView.setVisibility(tags.size() > 0 ? View.VISIBLE : View.GONE);
    }

    private void initTagsClicklistener() {
        for (TextView item : tags) {
            item.setOnClickListener(this);
        }
    }

    public void setData(List<ProductTag> tagsData) {
        if (null == tagsData) {
            return;
        }
        Log.v("tabbar", "tagsData " + tagsData.size());
        this.tagsData = tagsData;
        initTagsLayout();
    }

    private void focusMove(View v) {
        focusView.animate().translationX(v.getX() +
                ((0 == v.getWidth()) ? 0 : v.getWidth() / 2 - focusView.getWidth() / 2));
    }


    private void selectView(TextView view) {
        view.setTextColor(colorFocusedText);
        if (null != selectedView) {
            selectedView.setTextColor(colorUnfocusedText);
        }
        selectedView = view;
    }

    @Override
    public void onClick(View v) {
        if (null != mLinearIndicatorListener) {
            mLinearIndicatorListener.onTarBarItemClicked(this, v.getTag());
        }
        focusMove(v);
        selectView((TextView) v);
    }

    public void setTarBarItemClickListener(AbstLinearIndicator.LinearIndicatorListener tarBarItemClickListener) {
        this.mLinearIndicatorListener = tarBarItemClickListener;
    }

    public int getItemCount() {
        return tags.size();
    }
}
