package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.fm.fmlib.dao.ProductTag;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.bean.HomeProAeItem;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ProTagListAdapter extends BaseAdapter {
    List<ProductTag> items;
    int currentIndex = 0;
    private Context mContext;
    private AdapterView.OnItemClickListener itemClickListener;

    public ProTagListAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<ProductTag>();
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
        ProductTag item = items.get(position);
        ViewHolderHelper holderHelper = ViewHolderHelper.get(mContext, position, convertView, parent, R.layout.list_item_pro_tag_item);
        holderHelper.setText(R.id.tag_name, item.getTag_name());
        holderHelper.setVisibility(R.id.tag_chosed, currentIndex == position ? View.VISIBLE : View.GONE);
        return holderHelper.mConvertView;
    }

    public void onItemClick(int index) {
        currentIndex = index;
        this.notifyDataSetInvalidated();
    }

    public void setdata(List<ProductTag> items){
        this.items = items;
        this.notifyDataSetInvalidated();
    }

    public ProductTag getCurrentTag(){
        if(currentIndex>=0 && currentIndex<getCount()){
            return items.get(currentIndex);
        }
        return null;
    }
}
