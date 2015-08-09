package com.totour.qieyoumerchanthd.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.utils.DisplayUtil;
import com.totour.qieyoumerchanthd.R;
import com.totour.qieyoumerchanthd.bean.HomeNaviItem;
import com.totour.qieyoumerchanthd.util.ViewHolderHelper;
import com.totour.qieyoumerchanthd.widget.ColorTextButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeNavigationAdapter extends BaseAdapter {
    List<HomeNaviItem> items;
    int currentIndex = 0;
    private Context mContext;
    private AdapterView.OnItemClickListener itemClickListener;

    public HomeNavigationAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<HomeNaviItem>();
        initData();
    }

    private void initData() {
        items.clear();
        //store
        HomeNaviItem item = new HomeNaviItem();
        item.local_icon = R.drawable.test_stor_icon;
        item.title = mContext.getString(R.string.home_store_title);
        item.tag = MainController.HomeMenu.STORE;
        items.add(item);

        //mall
        item = new HomeNaviItem();
        item.local_icon = R.drawable.test_mall_icon;
        item.title = mContext.getString(R.string.home_mall_title);
        item.tag = MainController.HomeMenu.MALL;
        items.add(item);

        //code
        item = new HomeNaviItem();
        item.local_icon = R.drawable.test_code_icon;
        item.title = mContext.getString(R.string.home_code_title);
        item.tag = MainController.HomeMenu.CODE;
        items.add(item);

        //manager
        item = new HomeNaviItem();
        item.local_icon = R.drawable.test_manager_icon;
        item.title = mContext.getString(R.string.home_manager_title);
        item.tag = MainController.HomeMenu.MANAGER;
        items.add(item);

        //setting
        item = new HomeNaviItem();
        item.local_icon = R.drawable.test_setting_icon;
        item.title = mContext.getString(R.string.home_setting_title);
        item.tag = MainController.HomeMenu.SETTING;
        items.add(item);
    }

    @Override
    public int getCount() {
        return null == items ? 0 : items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position).tag;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HomeNaviItem item = items.get(position);
        HomeNaviHolderHelper holderHelper = HomeNaviHolderHelper.get(mContext, position, convertView, parent, R.layout.adapter_home_navi_item);
        holderHelper
                .setSelected(R.id.home_navitem, currentIndex == position)
                .setCompoundDrawablesWithIntrinsicBounds(R.id.home_navitem, 0, item.local_icon, 0, 0)
                .setText(R.id.home_navitem, item.title);
        return holderHelper.mConvertView;
    }

    public void onItemClick(int index) {
        currentIndex = index;
        this.notifyDataSetInvalidated();
    }
}
