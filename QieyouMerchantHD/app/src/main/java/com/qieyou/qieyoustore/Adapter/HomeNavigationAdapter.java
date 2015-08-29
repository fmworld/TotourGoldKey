package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.bean.HomeNaviItem;
import com.qieyou.qieyoustore.ui.widget.ColorTextButton;

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
        item.local_icon = R.drawable.tabbar_shop_s;
        item.local_icon_u=R.drawable.tabbar_shop_n;
        item.title = mContext.getString(R.string.home_store_title);
        item.tag = MainController.HomeMenu.STORE_GALLERY;
        items.add(item);

        //mall
        item = new HomeNaviItem();
        item.local_icon = R.drawable.tabbar_special_s;
        item.local_icon_u=R.drawable.tabbar_special_n;
        item.title = mContext.getString(R.string.home_mall_title);
        item.tag = MainController.HomeMenu.MALL;
        items.add(item);

        //code
        item = new HomeNaviItem();
        item.local_icon = R.drawable.tabbar_quick_code_s;
        item.local_icon_u = R.drawable.tabbar_quick_code_n;
        item.title = mContext.getString(R.string.home_code_title);
        item.tag = MainController.HomeMenu.CODE;
        items.add(item);

        //manager
        item = new HomeNaviItem();
        item.local_icon = R.drawable.tabbar_manager_s;
        item.local_icon_u = R.drawable.tabbar_manager_n;
        item.title = mContext.getString(R.string.home_manager_title);
        item.tag = MainController.HomeMenu.MANAGER;
        items.add(item);

        //setting
        item = new HomeNaviItem();
        item.local_icon = R.drawable.tabbar_setting_s;
        item.local_icon_u = R.drawable.tabbar_setting_n;
        item.title = mContext.getString(R.string.home_setting_title);
        item.tag = MainController.HomeMenu.SETTING;
        items.add(item);

        //add
        List<LaunchProfile> profiles=  TourApplication.instance().getDaoLaunProfile().getLaunProfiles(ProductState.LaunchProfileType.slider.toString());
        if(null !=profiles && 0 < profiles.size()){
            item = new HomeNaviItem();
            item.tag = MainController.HomeMenu.WEB;
            item.icon = profiles.get(0).getImg();
            item.title = profiles.get(0).getLink();
            items.add(item);
        }
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
        HomeNaviItem item = items.get(position);
        View view;
        if(item.tag == MainController.HomeMenu.WEB){
            view = View.inflate(mContext, R.layout.adapter_home_navi_web_item, null);
            ((SimpleDraweeView)view.findViewById(R.id.navi_web_item)).setImageURI(Uri.parse(item.icon));
        }else{
            view = View.inflate(mContext, R.layout.adapter_home_navi_item, null);
            (view.findViewById(R.id.home_navitem)).setSelected(currentIndex == position);
            ((ColorTextButton)view.findViewById(R.id.home_navitem))
                    .setCompoundDrawablesWithIntrinsicBounds(0,
                            currentIndex == position?item.local_icon:item.local_icon_u, 0, 0);
            ((ColorTextButton)view.findViewById(R.id.home_navitem)) .setText(item.title);
        }

        return view;
    }

    public void onItemClick(int index) {
        currentIndex = index;
        this.notifyDataSetInvalidated();
    }
}
