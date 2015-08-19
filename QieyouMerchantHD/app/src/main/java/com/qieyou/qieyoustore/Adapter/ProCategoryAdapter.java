package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.fm.fmlib.dao.CategoryTitle;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ProCategoryAdapter extends BaseAdapter {
    List<CategoryTitle> items;
    int currentIndex = 0;
    private Context mContext;
    private AdapterView.OnItemClickListener itemClickListener;

    public ProCategoryAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<CategoryTitle>();
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
        CategoryTitle item = items.get(position);
        ViewHolderHelper holderHelper = ViewHolderHelper.get(mContext, position, convertView, parent, R.layout.list_item_pro_category_item);
        holderHelper.setText(R.id.tag_category_name, item.getName());

        holderHelper.mConvertView.setBackgroundColor(mContext.getResources()
                .getColor(currentIndex == position ? R.color.home_mgr_pro_category_select_bg : R.color.home_mgr_pro_tag_new_title_bg));
        return holderHelper.mConvertView;
    }

    public void onItemClick(int index) {
        currentIndex = index;
        this.notifyDataSetInvalidated();
    }

    public void setdata(List<CategoryTitle> items) {
        this.items = items;
        this.notifyDataSetInvalidated();
    }

    public CategoryTitle getCurrentTag() {
        if (currentIndex >= 0 && currentIndex < getCount()) {
            return items.get(currentIndex);
        }
        return null;
    }
}
