package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tour.TourConfig;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.CodeBusinessMap;
import com.qieyou.qieyoustore.util.TourStringUtil;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class MallAddAdapter extends PagerAdapter implements View.OnClickListener {
    List<LaunchProfile> items;
    List<View> views;
    private Context mContext;
    public MallAddAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<>();
        views = new ArrayList<>();
    }

    public View getItem(int position) {
        View view = null;
        if(position < views.size()){
            view = views.get(position);

        }
        return initView(view, items.get(position));
    }

    private View initView(View view, LaunchProfile item){
        if(null ==view){
            view = View.inflate(mContext, R.layout.list_item_mall_add_item,null);
        }

        ((SimpleDraweeView)(view)).setImageURI(Uri.parse(item.getImg()));
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {

        container.removeView((View) object);//删除页卡
        if(views.size()> position){
            views.set(position, null);
        }
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        View view = getItem(position);
        container.addView(view);//添加页卡
        while (views.size() <= position) {
            views.add(null);
        }
        views.set(position, view);
        return view;
    }

    @Override
    public int getCount() {
        return  null == items ? 0:items.size();//返回页卡的数量
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }





    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setdata(List<LaunchProfile> items) {
        this.items = items;
        this.views.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Log.v("submitorder", "v  " + (String) v.getTag());
    }
}
