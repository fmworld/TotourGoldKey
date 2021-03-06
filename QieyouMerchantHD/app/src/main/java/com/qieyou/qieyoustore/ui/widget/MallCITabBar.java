package com.qieyou.qieyoustore.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fm.fmlib.dao.CategoryList;
import com.fm.fmlib.dao.CategoryTitle;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class MallCITabBar extends LinearLayout implements View.OnClickListener{
    private String selectedItem;
    private View currentView;
    private SparseArray<View> itemViews;
    private List<CategoryList> titles;
    private MallTabBar.MTBItemClickListener clickListener;

    public MallCITabBar(Context context) {
        super(context);
        init();
    }

    public MallCITabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MallCITabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnMTBClickListener(MallTabBar.MTBItemClickListener listenr){
        this.clickListener =listenr;
    }

    private void init(){
        itemViews = new SparseArray<>();
        titles = new ArrayList<>();
    }
    public void setData(List<CategoryList> titles){
        if(null == titles){
            return;
        }
        this.removeAllViews();
        CategoryList item = new CategoryList();
        item.setCategory_id("0");
        item.setName(this.getContext().getResources().getString(R.string.mall_cate_id_all));
        this.titles = titles;
        this.titles.add(0,item);

        View view;
        for(CategoryList title:titles){
            view = createItemView(title);
            LayoutParams params = new LayoutParams(DisplayUtil.dip2px(this.getContext(),116),DisplayUtil.dip2px(this.getContext(),50));
            params.gravity = Gravity.CENTER;
            this.addView(view,params);
        }
        itemClicked(this.getChildAt(0));
    }

    private View createItemView(CategoryList title){
        View view_layout = View.inflate(this.getContext(),R.layout.widget_mall_ci_item,null);
        TextView view = (TextView)view_layout.findViewById(R.id.mall_ci_item);
        view.setTextColor(this.getContext().getResources().getColor(R.color.gray_12));
        view.setTextSize(14);
        view.setGravity(Gravity.CENTER);
        view.setText(title.getName());
        view_layout.setOnClickListener(this);
        view_layout.setTag(title.getCategory_id());
        return view_layout;
    }

    private void itemClicked(View view){
        previousUnSelected();
        currentSelected(view);
        selectedItem = (String)view.getTag();
        if(null != clickListener){
            clickListener.itemClicked(this, selectedItem);
        }
    }

    private void previousUnSelected() {
        if (null != currentView) {
            currentView.findViewById(R.id.mall_ci_item).setBackgroundColor(this.getResources().getColor(R.color.black_11));
            ((TextView)currentView.findViewById(R.id.mall_ci_item)).setTextColor(this.getContext().getResources().getColor(R.color.gray_12));
        }
    }

    private void currentSelected(View view){
        view.findViewById(R.id.mall_ci_item).setBackgroundResource(R.drawable.mall_cate_id_selected_bg);
        ((TextView)view.findViewById(R.id.mall_ci_item)).setTextColor(this.getContext().getResources().getColor(R.color.yellow));
        currentView = view;
    }

    @Override
    public void onClick(View v) {
        itemClicked(v);
    }

    public String getSelectedItem() {
        return selectedItem;
    }
}
