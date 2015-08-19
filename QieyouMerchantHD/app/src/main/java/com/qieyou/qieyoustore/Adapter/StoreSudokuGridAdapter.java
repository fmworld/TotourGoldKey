package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.tour.TourConfig;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.TourStringUtil;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class StoreSudokuGridAdapter extends BaseAdapter {
    List<ProductBreviary> items;
    private Context mContext;

    public StoreSudokuGridAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<>();
    }

    public void setDataSeq(String items_seq){

        if(TourStringUtil.isNULLorEmpty(items_seq) )
            return;
        items.clear();
        String[] tags = items_seq.split(",");
        ProductBreviary p;
        for(String item: tags){
            p = new ProductBreviary();
            p.setProduct_id(item);
            items.add(p);
        }
        this.notifyDataSetInvalidated();
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
        ProductBreviary item = items.get(position);
        ViewHolderHelper holderHelper = ViewHolderHelper.get(mContext, position, convertView, parent, R.layout.list_item_store_sudoku_grid_item);
        holderHelper.setText(R.id.store_sdk_item_title,
                TourStringUtil.isNULLorEmpty(item.getProduct_name())?mContext
                        .getString(R.string.store_item_load):item.getProduct_name());
        holderHelper.setText(R.id.store_sdk_item_price,
                TourStringUtil.isNULLorEmpty(item.getPrice()) ? mContext
                        .getString(R.string.store_item_price_init) : item.getPrice());
        holderHelper.setImageURI(R.id.store_sdk_post, Uri.parse(TourConfig.instance().getImageRoot() + "/" + item.getThumb()));
        return holderHelper.mConvertView;
    }


    public void setdata(List<ProductBreviary> items) {
        this.items = items;
        this.notifyDataSetInvalidated();
    }
}
