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

import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/18.
 */
public abstract class AbstLinearIndicator<T,V extends View> extends RelativeLayout implements View.OnClickListener {
    public interface TarBarItemClickListener {
        public void onTarBarItemClicked(String item_seq);
    }

    private List<T> tagsData;
    private int focusWidth;
    private int colorFocusedText;
    private int colorUnfocusedText;
    private float textSize;

    private View focusView;
    private TarBarItemClickListener tarBarItemClickListener;

    private List<V> tags;
    private V selectedView;
    private LinearLayout tagsLayout;


    public AbstLinearIndicator(Context context) {
        super(context);
        init(null);
    }

    public AbstLinearIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AbstLinearIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
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
        if(null == tagsLayout){
            LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            tagsLayout = new LinearLayout(this.getContext());
            tagsLayout.setLayoutParams(params);
            this.addView(tagsLayout, 0);
        }

        tags.clear();
        tagsLayout.removeAllViews();

        for (T item : tagsData) {
            initTag(item);
        }
        initTagsClicklistener();
        if (0 < tags.size()) {
            tags.get(0).callOnClick();
        }
        initFocus();
    }

    public abstract V getTag(T dataItem);
    private void initTag(T dataItem) {
        if (null != tagsLayout) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(focusWidth, LayoutParams.MATCH_PARENT);
            V view = getTag(dataItem);
            view.setLayoutParams(params);
            tagsLayout.addView(view);
            tags.add(view);
        }
    }

    private void initFocus() {
        if (null == focusView) {
            LayoutParams params = new LayoutParams(focusWidth, LayoutParams.MATCH_PARENT);
            params.topMargin = DisplayUtil.dip2px(this.getContext(), 5);
            params.bottomMargin = DisplayUtil.dip2px(this.getContext(), 5);
            focusView = initFocusUI();
            focusView.setLayoutParams(params);
            focusView.setBackgroundResource(R.drawable.home_store_tab_bar_foucus_bg);
            focusView.setVisibility(View.INVISIBLE);
            this.addView(focusView, 0);
        }

        focusView.setVisibility(tags.size() > 0 ? View.VISIBLE : View.GONE);
    }

    public abstract View initFocusUI();
    private void initTagsClicklistener() {
        for (V item : tags) {
            item.setOnClickListener(this);
        }
    }

    public void setData(List<T> tagsData) {
        if (null == tagsData) {
            return;
        }
        Log.v("tabbar", "tagsData " + tagsData.size());
        this.tagsData = tagsData;
        initTagsLayout();
    }

    private void focusMove(View v) {
        focusView.animate().translationX(v.getX() + v.getWidth() / 2 - focusView.getWidth() / 2);
    }


    private void selectView(V view) {
        if(null != selectedView){
            viewUnfocused(selectedView);
        }
        viewFocused(view);
        selectedView = view;
    }

    public abstract void viewFocused(V focused);
    public abstract void viewUnfocused(V unfocused);

    @Override
    public void onClick(View v) {
        if (null != tarBarItemClickListener) {
            tarBarItemClickListener.onTarBarItemClicked((String) v.getTag());
        }
        focusMove(v);
        selectView((V)v);
    }

    public void setTarBarItemClickListener(TarBarItemClickListener tarBarItemClickListener) {
        this.tarBarItemClickListener = tarBarItemClickListener;
    }

    public int getItemCount(){
        return tags.size();
    }
}
