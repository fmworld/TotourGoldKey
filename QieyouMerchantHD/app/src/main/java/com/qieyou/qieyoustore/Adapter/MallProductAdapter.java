package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.fm.fmlib.dao.CategoryTitle;
import com.fm.fmlib.dao.Product;
import com.fm.fmlib.tour.TourConfig;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.TourPicConfig;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class MallProductAdapter extends BaseAdapter {
    List<Product> items;
    int currentIndex = 0;
    private Context mContext;
    private AdapterView.OnItemClickListener itemClickListener;

    public MallProductAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<>();
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
        Product item = items.get(position);
        ViewHolderHelper holderHelper = ViewHolderHelper.get(mContext,
                position, convertView, parent, R.layout.list_item_mall_product);
        holderHelper.setImageURI(R.id.mall_pro_post,
                Uri.parse(TourConfig.instance().getImageRoot() + "/" + item.getThumb()));
        holderHelper.setText(R.id.mall_pro_content, item.getContent());
        holderHelper.setText(R.id.mall_pro_name, item.getProduct_name());
        holderHelper.setText(R.id.mall_pro_price, Html.fromHtml(mContext
                .getString(R.string.mall_product_price_str, item.getOld_price(), item.getPrice())));
        holderHelper.setText(R.id.mall_pro_content, Html.fromHtml(mContext.getString(R.string.mall_product_content_htm_str, item.getContent(), item.getShelf_count(), item.getBought_count() ),imgGetter,null));


        return holderHelper.mConvertView;
    }

    public void onItemClick(int index) {
        currentIndex = index;
        this.notifyDataSetInvalidated();
    }

    public void setdata(List<Product> items) {
        this.items = items;
        this.notifyDataSetInvalidated();
    }

    Html.ImageGetter imgGetter = new Html.ImageGetter() {
        public Drawable getDrawable(String source) {
            Drawable drawable = null;

            drawable = mContext.getResources().getDrawable(R.drawable.mall_pro_content_icon); //显示本地图片
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable
                    .getIntrinsicHeight());
            return drawable;
        }
    };
}
