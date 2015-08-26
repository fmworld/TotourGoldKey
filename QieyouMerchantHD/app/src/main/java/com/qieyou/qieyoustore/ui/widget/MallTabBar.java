package com.qieyou.qieyoustore.ui.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fm.fmlib.dao.CategoryTitle;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.WheelDatePick;
import com.qieyou.qieyoustore.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class MallTabBar extends LinearLayout implements View.OnClickListener{
    private String selectedItem;

    public interface MTBItemClickListener{
        void itemClicked(View view, String ca_id);
    }
    private View currentView;
    private SparseArray<View> itemViews;
    private List<CategoryTitle> titles;
    private MTBItemClickListener clickListener;

    public MallTabBar(Context context) {
        super(context);
        init();
    }

    public MallTabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MallTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setOnMTBClickListener(MTBItemClickListener listenr){
        this.clickListener =listenr;
    }

    private void init(){
        itemViews = new SparseArray<>();
        titles = new ArrayList<>();
        initIcons();
    }
    public void setData(List<CategoryTitle> titles){
        if(null == titles){
            return;
        }
        this.removeAllViews();
        this.titles.clear();
        this.titles = titles;
        View view;
        for(CategoryTitle title:titles){
            view = createItemView(title);
            LayoutParams params = new LayoutParams(DisplayUtil.dip2px(this.getContext(),116),DisplayUtil.dip2px(this.getContext(),68));
            view.setPadding(0,0,0,DisplayUtil.dip2px(this.getContext(),5));
            this.addView(view,params);
        }
        onClick(this.getChildAt(0));
    }

    private View createItemView(CategoryTitle title){
        View view = View.inflate(this.getContext(), R.layout.list_item_mall_category_item, null);
        ((ImageView)view.findViewById(R.id.mall_category_item_icon)).setImageResource(icons_unselete.get(Integer.valueOf(title.getId())));
        ((TextView)view.findViewById(R.id.mall_category_item_name)).setTextColor(this.getResources().getColor(R.color.white));
        ((TextView)view.findViewById(R.id.mall_category_item_name)).setText(title.getName());
        view.setTag(title.getId());
        view.setOnClickListener(this);
        return view;
    }

    private void itemClicked(View view){
        previousUnSelected();
        currentSelected(view);
        selectedItem = (String)view.getTag();
        if(null != clickListener){
            clickListener.itemClicked(this,selectedItem);
        }
    }

    private void previousUnSelected() {
        if (null != currentView) {
            currentView.setBackgroundColor(this.getResources().getColor(R.color.black_12));
            ((TextView)currentView.findViewById(R.id.mall_category_item_name))
                    .setTextColor(this.getResources().getColor(R.color.white));
            ((ImageView)currentView.findViewById(R.id.mall_category_item_icon))
                    .setImageResource(icons_unselete.get(Integer.valueOf((String)currentView.getTag())));
        }
    }

    private void currentSelected(View view){
        ((TextView)view.findViewById(R.id.mall_category_item_name))
                .setTextColor(this.getResources().getColor(R.color.yellow));
        ((ImageView)view.findViewById(R.id.mall_category_item_icon))
                .setImageResource(icons_selete.get(Integer.valueOf((String)view.getTag())));
        view.setBackgroundColor(this.getResources().getColor(R.color.black_1));
        currentView = view;
    }

    @Override
    public void onClick(View v) {
        itemClicked(v);
    }

    private SparseArray<Integer> icons_selete= new SparseArray<>();
    private SparseArray<Integer> icons_unselete= new SparseArray<>();
    private void initIcons(){
        icons_unselete.put(1,R.drawable.mall_hotel_unselected);
        icons_selete.put(1,R.drawable.mall_hotel_selected);

        icons_unselete.put(2,R.drawable.mall_food_unselected);
        icons_selete.put(2,R.drawable.mall_food_selected);

        icons_unselete.put(3,R.drawable.mall_entir_unselected);
        icons_selete.put(3,R.drawable.mall_entir_selected);

        icons_unselete.put(4,R.drawable.mall_walk_unselected);
        icons_selete.put(4,R.drawable.mall_walk_selected);

        icons_unselete.put(5,R.drawable.mall_trave_unselected);
        icons_selete.put(5,R.drawable.mall_trave_selected);

        icons_unselete.put(6,R.drawable.mall_shop_unselected);
        icons_selete.put(6,R.drawable.mall_shop_selected);

        icons_unselete.put(7,R.drawable.mall_protect_unselect);
        icons_selete.put(7,R.drawable.mall_protect_selected);
    }

    public String getSelectedItem() {
        return selectedItem;
    }
}
