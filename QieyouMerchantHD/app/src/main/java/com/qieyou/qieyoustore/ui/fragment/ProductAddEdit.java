package com.qieyou.qieyoustore.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.tour.bean.ProductInfo;
import com.qieyou.qieyoustore.Adapter.HomeMgrProaeAdapter;
import com.qieyou.qieyoustore.Adapter.HomeMgrProaePicsAdapter;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.util.DateUtil;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ProductAddEdit extends AnimListenFragment implements InnController.ProductAeUi, View.OnClickListener {
    private InnController.InnProductUICallbacks mInnProductUICallbacks;
    private View content;
    private String order_id;
    private HomeMgrProaePicsAdapter picsAdapter;
    public ProductAddEdit() {

    }

    public static ProductAddEdit create() {
        ProductAddEdit fragment = new ProductAddEdit();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_manager_product_addedit, null);
        order_id = (String)this.getArguments().get("order");
        picsAdapter =new HomeMgrProaePicsAdapter(this.getActivity());
        ((GridView) content.findViewById(R.id.home_mgr_pro_ae_pics)).setAdapter(picsAdapter);
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        if(null != order_id){
            mInnProductUICallbacks.fetchProductInfo(order_id);
        }else{
            mInnProductUICallbacks.updateProductInfo();
        }
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    InnController getController() {
        return MerchanthdApplication.instance().getmMainController().getInnController();
    }



    @Override
    public void setCallbacks(InnController.InnUiCallbacks callbacks) {
        mInnProductUICallbacks =(InnController.InnProductUICallbacks)callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
    }


    @Override
    public void initEditView(ProductInfo info) {
        ((TextView)content.findViewById(R.id.home_mgr_pro_ae_title)).setText(R.string.home_mgr_pro_ae_edit);
        ((TextView)content.findViewById(R.id.pro_info_name_value)).setText(info.product_name);
        ((TextView)content.findViewById(R.id.pro_info_label_value)).setText(info.tag_name);
        ((TextView)content.findViewById(R.id.pro_info_type_value)).setText(info.category);
        ((TextView)content.findViewById(R.id.pro_old_price_value)).setText(this.getString(R.string.home_mgr_pro_ae_price_str,info.old_price));
        ((TextView)content.findViewById(R.id.pro_selling_price_value)).setText(this.getString(R.string.home_mgr_pro_ae_price_str,info.price));
        ((TextView)content.findViewById(R.id.pro_quantity_value)).setText(info.quantity);

        ((TextView)content.findViewById(R.id.pro_price_deadline_value)).setText(DateUtil.formatDate(Long.valueOf(info.tuan_end_time+"000")));
        ((TextView)content.findViewById(R.id.pro_intro_intro_value)).setText(info.content);
        ((TextView)content.findViewById(R.id.pro_intro_parchase_value)).setText(info.booking_info);
        ((TextView)content.findViewById(R.id.pro_intro_intro_value)).setText(info.note);
        ((TextView)content.findViewById(R.id.pro_recommend_words_vaule)).setText(info.content);
        ((TextView)content.findViewById(R.id.pro_recommend_words_vaule)).setText(info.content);
        ((TextView)content.findViewById(R.id.pro_recommend_travel_comment_value)).setText(info.traveler);
        ((TextView)content.findViewById(R.id.pro_recommend_boss_comment_value)).setText(info.innholder);
        picsAdapter.setStrData(info.product_images);

    }

    @Override
    public void initAddView() {
        ((TextView)content.findViewById(R.id.home_mgr_pro_ae_title)).setText(R.string.home_mgr_pro_ae_add);
    }
}
