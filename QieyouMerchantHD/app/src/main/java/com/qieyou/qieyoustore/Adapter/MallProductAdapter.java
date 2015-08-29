package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.dao.Product;
import com.fm.fmlib.dao.ProductTagDao;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.tour.TourConfig;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.ProductTagsDialog;
import com.qieyou.qieyoustore.util.CodeBusinessMap;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class MallProductAdapter extends BaseAdapter implements View.OnClickListener {
    List<Product> items;
    int currentIndex = 0;
    private Context mContext;
    private AdapterView.OnItemClickListener itemClickListener;
    private ProductTagsDialog tagsDialog;
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

        holderHelper.setText(R.id.mall_pro_content,
                Html.fromHtml(mContext.getString(R.string.mall_product_content_htm_str,
                        item.getContent(), item.getShelf_count(), item.getBought_count()), imgGetter, null));

        holderHelper.setClickListener(R.id.mall_pro_item_layout, this);
        initShelfState((TextView) holderHelper.mConvertView.findViewById(R.id.mall_shelf_state),
                "1".equals(item.getOn_shelves()), position);

        initSaleState((TextView) holderHelper.mConvertView.findViewById(R.id.mall_pro_sale_state)
                , CodeBusinessMap.productStateStr(item), item, position);

        String showTip = TourApplication.instance().getDaoProperty().getValue(ProductState.Tip);
        if(null ==showTip ||"0".equals(showTip)){
            holderHelper.setText(R.id.mall_pro_price, Html.fromHtml(mContext
                    .getString(R.string.mall_product_price_str, item.getOld_price(), item.getPrice())));
        }else{
            holderHelper.setText(R.id.mall_pro_price, Html.fromHtml(mContext
                    .getString(R.string.mall_product_price_with_tip_str, item.getOld_price(), item.getPrice(), item.getAgent())));
        }

        return holderHelper.mConvertView;
    }

    private void initShelfState(TextView view, boolean shelfed, int postion) {

        if (shelfed) {
            view.setText(mContext.getString(R.string.mall_pro_has_shelved));
            view.setBackgroundResource(R.drawable.bg_coners_gray_round);
            view.setTextColor(mContext.getResources().getColor(R.color.green));
            view.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mall_pro_has_shelf
                    , 0, 0);
            view.setOnClickListener(null);
        } else {
            view.setText(mContext.getString(R.string.mall_pro_to_shelve));
            view.setBackgroundResource(R.drawable.bg_coners_bule_round);
            view.setTextColor(mContext.getResources().getColor(R.color.white));
            view.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.mall_pro_to_shelf
                    , 0, 0);
            view.setOnClickListener(this);
            view.setTag(postion);
        }
    }

    private void initSaleState(TextView view, boolean saleable,Product product, int postion){
        if (saleable) {
            view.setText(mContext.getString(R.string.sale_state_able));
            view.setBackgroundResource(R.drawable.bg_coners_oringe_round);

            view.setOnClickListener(this);
            view.setTag(postion);
        } else {
            view.setText(CodeBusinessMap.productUnsaleStateStr(product));
            view.setBackgroundResource(R.drawable.bg_coners_gray_12_round);
            view.setOnClickListener(null);
        }


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

    @Override
    public void onClick(final View v) {
        if(R.id.mall_shelf_state == v.getId()){
            if(null == tagsDialog){
                tagsDialog = new ProductTagsDialog(mContext, R.style.translucent);

            }
            tagsDialog.setConfirmListener(new ProductTagsDialog.ConfirmListener() {
                @Override
                public void tagSelected(String tag_id) {
                    TourApplication.instance().getmBus()
                            .post(new ProductState.ProductChangeShelfStateEvent
                                    (tag_id,items.get((int)v.getTag()).getProduct_id(),"up" ));
                    Log.v("proadd", "tag_id "+tag_id+"  postion  "+(int)v.getTag());
                }
            });
            tagsDialog.show();

        }else if(R.id.mall_pro_item_layout == v.getId()){
            Product pro = (Product) getItem(((ViewHolderHelper) v.getTag()).position);
            Bundle bundle = new Bundle();
            bundle.putString("item", pro.getProduct_id());
            ((BaseTourActivity) (mContext))
                    .getDisplay()
                    .showHomeSecondContent(MainController.HomeMenu.PRO_DETAIL, bundle);
        }else if(R.id.mall_pro_sale_state == v.getId()){
            TourApplication.instance().getmBus()
                    .post(new ProductState.ProductFetchSubmitUrlEvent(items.get((int)v.getTag()).getProduct_id()));
        }
    }
}
