package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.fm.fmlib.controllers.MainController;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.bean.HomeNaviItem;
import com.qieyou.qieyoustore.bean.HomeProAeItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeMgrProaeAdapter extends BaseAdapter {
    public enum ProItem{
        info,
        price,
        intro,
        recommend,
    }
    List<HomeProAeItem> items;
    int currentIndex = 0;
    private Context mContext;
    private AdapterView.OnItemClickListener itemClickListener;

    public HomeMgrProaeAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<HomeProAeItem>();
        initData();
    }

    private void initData() {
        items.clear();
        //info
        HomeProAeItem item = new HomeProAeItem();
        item.item = ProItem.info;
        items.add(item);

        item = new HomeProAeItem();
        item.item = ProItem.intro;
        items.add(item);

        item = new HomeProAeItem();
        item.item = ProItem.price;
        items.add(item);

        item = new HomeProAeItem();
        item.item = ProItem.recommend;
        items.add(item);

    }

    @Override
    public int getCount() {
        return null == items ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeProAeItem item = items.get(position);
        HomeNaviHolderHelper holderHelper = HomeNaviHolderHelper.get(mContext, position, convertView, parent, createLayout(item.item));
//        holderHelper
//                .setSelected(R.id.home_navitem, currentIndex == position)
//                .setCompoundDrawablesWithIntrinsicBounds(R.id.home_navitem, 0, item.local_icon, 0, 0)
//                .setText(R.id.home_navitem, item.title);
        return holderHelper.mConvertView;
    }

    public void onItemClick(int index) {
        currentIndex = index;
        this.notifyDataSetInvalidated();
    }

    private int createLayout(ProItem item){
        if(ProItem.info == item){
            return R.layout.list_item_pro_addedit_pro_info;
        }

        if(ProItem.intro == item){
            return R.layout.list_item_pro_addedit_pro_intro;
        }

        if(ProItem.price == item){
            return R.layout.list_item_pro_addedit_pro_price;
        }

        if(ProItem.recommend == item){
            return R.layout.list_item_pro_addedit_pro_recommend;
        }
        return 0;
    }
}
