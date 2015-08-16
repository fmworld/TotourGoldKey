package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.fm.fmlib.tour.TourConfig;
import com.fm.fmlib.utils.StringUtils;
import com.qieyou.qieyoustore.HomeAcitvity;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.bean.HomeMgrProPicItem;
import com.qieyou.qieyoustore.bean.HomeProAeItem;
import com.qieyou.qieyoustore.util.TourStringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeMgrProaePicsAdapter extends BaseAdapter implements View.OnClickListener {
    List<HomeMgrProPicItem> items;
    int currentIndex = 0;
    private Context mContext;

    public HomeMgrProaePicsAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<HomeMgrProPicItem>();
//        initData();
    }

    public void setStrData(String pics) {
        if (TourStringUtil.isNULLorEmpty(pics)) {
            return;
        }
        HomeMgrProPicItem ppItem;
        String[] picArr = pics.split(",");
        items.clear();
        for (String url : picArr) {
            ppItem = new HomeMgrProPicItem();
            ppItem.local = false;
            ppItem.uri = Uri.parse(TourConfig.instance().getImageRoot() + "/" + url);
            items.add(ppItem);
        }
        items.add(new HomeMgrProPicItem());
        this.notifyDataSetInvalidated();
    }

    public void addLocalPicItem(Uri uri) {
        HomeMgrProPicItem ppItem = new HomeMgrProPicItem();
        ppItem.local = true;
        items.add(this.getCount() - 2, ppItem);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == items ? 0 : items.size() < 32 ? items.size() : 31;
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

        HomeMgrProPicsHolderHelper holderHelper;
        holderHelper = HomeMgrProPicsHolderHelper.get(mContext, position, convertView, parent, R.layout.grid_item_pro_pic);
        if (position == getCount() - 1) {
            holderHelper.setVisibility(R.id.home_mgr_pro_pic_delete_icon, View.GONE);
            holderHelper.setPlaceHoldImg(R.id.home_mgr_pro_pic, R.drawable.home_mgr_pro_add_pic);
            holderHelper.setClickListener(R.id.home_mgr_pro_pic, this, position);
        } else {
            HomeMgrProPicItem item = items.get(position);
            holderHelper.setVisibility(R.id.home_mgr_pro_pic_delete_icon, View.VISIBLE);
            holderHelper.setPlaceHoldImg(R.id.home_mgr_pro_pic, item.uri);
            holderHelper.setClickListener(R.id.home_mgr_pro_pic_delete_icon, this, position);
        }

        return holderHelper.mConvertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (R.id.home_mgr_pro_pic_delete_icon == v.getId()&&position < getCount() - 1) {
                items.remove(position);
                this.notifyDataSetChanged();
        } else if (R.id.home_mgr_pro_pic == v.getId() && position == getCount() - 1) {
            ((HomeAcitvity)mContext).selectPicFromCamera();
        }
    }
}
