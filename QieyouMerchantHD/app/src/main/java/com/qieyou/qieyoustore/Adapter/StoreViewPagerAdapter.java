package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.Product;
import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tour.TourConfig;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.CodeBusinessMap;
import com.qieyou.qieyoustore.util.TourStringUtil;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class StoreViewPagerAdapter extends PagerAdapter implements View.OnClickListener {
    List<ProductBreviary> items;
    List<View> views;
    private Context mContext;

    public StoreViewPagerAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<>();
        views = new ArrayList<>();
    }

    public View getItem(int position) {
        View view = null;
        if (position < views.size()) {
            view = views.get(position);

        }
        return initView(view, items.get(position));
    }

    private View initView(View view, ProductBreviary item) {
        ViewHolderHelper holderHelper = ViewHolderHelper.get(mContext, 0, view, null, R.layout.list_item_store_vp_item);
        if (item.getProduct_id().contains("http")) {
            holderHelper.setText(R.id.store_vp_item_title, "");
            holderHelper.setText(R.id.store_vp_item_price, "");
            holderHelper.setText(R.id.store_vp_item_old_price, "");
            List<LaunchProfile> profiles = TourApplication.instance().getDaoLaunProfile()
                    .getLaunProfiles(ProductState.LaunchProfileType.ad.toString());
            if(null != profiles&&0 < profiles.size()){
                holderHelper.setImageURI(R.id.store_vp_post, Uri.parse(profiles.get(0).getImg()));
            }

            holderHelper.setClickListener(R.id.store_vp_post, this, item.getProduct_id());
            holderHelper.setText(R.id.store_vp_item_bottom_button, mContext.getString(R.string.store_item_web_notify));
            holderHelper.setClickListener(R.id.store_vp_item_bottom_button, this, item.getProduct_id());
            holderHelper.setBackgroundResource(R.id.store_vp_item_bottom_button, R.drawable.store_vp_bottom_able_bg);
            holderHelper.setBackgroundColor(R.id.store_vp_bottom_layout, mContext.getResources().getColor(R.color.transparent));

            return holderHelper.mConvertView;
        }
        holderHelper.setText(R.id.store_vp_item_title,
                TourStringUtil.isNULLorEmpty(item.getProduct_name()) ? mContext
                        .getString(R.string.store_item_load) : item.getProduct_name());
        holderHelper.setText(R.id.store_vp_item_price,Html.fromHtml(mContext.getString(R.string.store_item_price,
                TourStringUtil.isNULLorEmpty(item.getPrice())? mContext
                        .getString(R.string.store_item_price_init):item.getPrice())));
        holderHelper.setText(R.id.store_vp_item_old_price, mContext.getString(R.string.store_item_old_price,
                TourStringUtil.isNULLorEmpty(item.getOld_price()) ? mContext
                        .getString(R.string.store_item_price_init) : item.getPrice()));
        holderHelper.setFlags(R.id.store_vp_item_old_price,
                Paint.STRIKE_THRU_TEXT_FLAG);
        holderHelper.setImageURI(R.id.store_vp_post, Uri.parse(TourConfig.instance().getImageRoot() + "/" + item.getThumb()));
        holderHelper.setClickListener(R.id.store_vp_post, this, item.getProduct_id());
        boolean saleable = CodeBusinessMap.productStateStr(item);
        holderHelper.setText(R.id.store_vp_item_bottom_button,
                saleable ? mContext
                        .getString(R.string.sale_state_able) : mContext
                        .getString(R.string.sale_state_unable));
        holderHelper.setClickListener(R.id.store_vp_item_bottom_button, saleable ? this : null, item.getProduct_id());
        holderHelper.setBackgroundResource(R.id.store_vp_item_bottom_button, saleable ? R.drawable.store_vp_bottom_able_bg : R.drawable.store_vp_bottom_unable_bg);
        holderHelper.setBackgroundColor(R.id.store_vp_bottom_layout, mContext.getResources().getColor(R.color.store_vp_item_bottom_bg));
        return holderHelper.mConvertView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);//删除页卡
        if (views.size() > position) {
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
        return null == items ? 0 : items.size();//返回页卡的数量
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }


    public void setDataSeq(String items_seq) {

        if (TourStringUtil.isNULLorEmpty(items_seq))
            return;
        items.clear();
        String[] tags = items_seq.split(",");
        ProductBreviary p;
        for (String item : tags) {
            p = new ProductBreviary();
            p.setProduct_id(item);
            items.add(p);
        }
        this.notifyDataSetChanged();
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setdata(List<ProductBreviary> items) {
        this.items = items;
        this.views.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        if (R.id.store_vp_item_bottom_button == v.getId()) {
            String vlaue = (String) v.getTag();
            if (null != vlaue && vlaue.contains("http")){
                Bundle bundle = new Bundle();
                bundle.putString("link", vlaue);
                ((BaseTourActivity) (mContext))
                        .getDisplay()
                        .showHomeSecondContent(MainController.HomeMenu.WEB, bundle);
            }else{
                TourApplication.instance().getmBus()
                        .post(new ProductState.ProductFetchSubmitUrlEvent(vlaue));
            }
        } else if (R.id.store_vp_post == v.getId()) {
            Bundle bundle = new Bundle();
            String vlaue = (String) v.getTag();
            if (null != vlaue && vlaue.contains("http")) {
                bundle.putString("link", vlaue);
                ((BaseTourActivity) (mContext))
                        .getDisplay()
                        .showHomeSecondContent(MainController.HomeMenu.WEB, bundle);
            } else {
                bundle.putString("item", vlaue);
                ((BaseTourActivity) (mContext))
                        .getDisplay()
                        .showHomeSecondContent(MainController.HomeMenu.PRO_DETAIL, bundle);
            }


        }
    }
}
