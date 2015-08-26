package com.qieyou.qieyoustore.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by zhoufeng'an on 2015/8/18.
 */
public abstract class AbstLinearIndicator<T, V extends View> extends RelativeLayout implements View.OnClickListener {
    protected int width;
    private int layer;

    public interface LinearIndicatorListener {
        void onTarBarItemClicked(View view, Object item_seq);

        void focusChanged(Object data);
    }

    private List<T> tagsData;
    private int focusWidth;
    private int colorFocusedText;
    private int colorUnfocusedText;
    private float textSize;
    protected T currentData;
    protected View focusView;
    private LinearIndicatorListener linearIndicatorListener;

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

    public void onSizeChanged(int nw, int nh, int ow, int oh) {
        super.onSizeChanged(nw, nh, ow, oh);
        width = nw;
        initView();
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
            layer = a.getInt(R.styleable.StoreTabBar_layer, 0);
            a.recycle();
        }


        tags = new ArrayList<>();
        tagsData = new ArrayList<>();
    }

    private void initView() {
        initTagsLayout();
    }

    private void initTagsLayout() {
        if (null == tagsLayout) {
            LayoutParams params = new LayoutParams(width, LayoutParams.MATCH_PARENT);
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
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width / (getItemCount() == 0 ? 1 : getItemCount()), LayoutParams.MATCH_PARENT);
            params.topMargin = DisplayUtil.dip2px(this.getContext(), 5);
            params.bottomMargin = DisplayUtil.dip2px(this.getContext(), 5);
            V view = getTag(dataItem);
            view.setLayoutParams(params);
            tagsLayout.addView(view);
            tags.add(view);

        }
    }

    private void initFocus() {
        if (null == focusView) {
            LayoutParams params = new LayoutParams(width / (getItemCount() == 0 ? 1 : getItemCount()), LayoutParams.MATCH_PARENT);
            params.topMargin = DisplayUtil.dip2px(this.getContext(), 2);
            params.bottomMargin = DisplayUtil.dip2px(this.getContext(), 2);
            focusView = initFocusUI();
            focusView.setLayoutParams(params);
            focusView.setBackgroundResource(R.drawable.store_indicator_mask_bg);
            focusView.setVisibility(View.INVISIBLE);
            this.addView(focusView, layer);
        } else {
            focusView.getLayoutParams().width = width / (getItemCount() == 0 ? 1 : getItemCount());
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

    private void focusMove(final View v) {
        focusView.animate().setListener(
                new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if (null != linearIndicatorListener) {
                            linearIndicatorListener.focusChanged(tags.indexOf(v)+1);
                        }
                    }
                }).translationX(v.getX());
    }


    private void selectView(V view) {
        if (null != selectedView) {
            viewUnfocused(selectedView);
        }
        viewFocused(view);
        selectedView = view;
    }

    public abstract void viewFocused(V focused);

    public abstract void viewUnfocused(V unfocused);

    @Override
    public void onClick(View v) {
        if (null != linearIndicatorListener) {
            int index = tags.indexOf(v);
            String item = (String)tagsData.get(index);
            linearIndicatorListener.onTarBarItemClicked(this,tagsData.get(index));
        }
        focusMove(v);
        selectView((V) v);
    }

    public void setLinearIndicatorListener(LinearIndicatorListener linearIndicatorListener) {
        this.linearIndicatorListener = linearIndicatorListener;
    }

    public int getItemCount() {
        return tagsData.size();
    }

    public void selecteItem(int position) {
        if (position >= 0 && position < tags.size()) {
            focusMove(tags.get(position));
            selectView(tags.get(position));
        }
    }
}
