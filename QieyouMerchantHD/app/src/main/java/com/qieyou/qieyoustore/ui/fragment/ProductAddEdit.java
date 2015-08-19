package com.qieyou.qieyoustore.ui.fragment;

import android.app.ActionBar;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.InnController;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.tasks.InnUpdateProductInfoRunnable;
import com.fm.fmlib.tour.bean.ProductInfo;
import com.fm.fmlib.tour.params.ProductParams;
import com.qieyou.qieyoustore.Adapter.HomeMgrProaeAdapter;
import com.qieyou.qieyoustore.Adapter.HomeMgrProaePicsAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.ui.widget.DateTimeDialog;
import com.qieyou.qieyoustore.ui.widget.KeywordsLinearLayout;
import com.qieyou.qieyoustore.ui.widget.ProTypeChooseDialog;
import com.qieyou.qieyoustore.ui.widget.ProductTagsDialog;
import com.qieyou.qieyoustore.ui.widget.WheelDatePick;
import com.qieyou.qieyoustore.util.DateUtil;
import com.qieyou.qieyoustore.util.ToastUtil;
import com.qieyou.qieyoustore.util.TourStringUtil;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class ProductAddEdit extends AnimListenFragment implements InnController.ProductAeUi, View.OnClickListener {
    private ProductInfo currentInfo;
    private InnController.InnProductUICallbacks mInnProductUICallbacks;
    private View content;
    private String order_id;
    private HomeMgrProaePicsAdapter picsAdapter;
    private DateTimeDialog dateTimeDialog;
    private ProductTagsDialog tagsDialog;
    private ProTypeChooseDialog typeDialog;
    private boolean inited;
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
        order_id = (String) this.getArguments().get("order");
        picsAdapter = new HomeMgrProaePicsAdapter(this.getActivity());
        ((GridView) content.findViewById(R.id.home_mgr_pro_ae_pics)).setAdapter(picsAdapter);
        content.findViewById(R.id.pro_price_deadline_value).setOnClickListener(this);
        content.findViewById(R.id.pro_info_label_value).setOnClickListener(this);
        content.findViewById(R.id.home_mgr_pro_ae_cancel).setOnClickListener(this);
        content.findViewById(R.id.pro_info_type_value).setOnClickListener(this);
        content.findViewById(R.id.home_mgr_pro_ae_bottom_done).setOnClickListener(this);
        content.findViewById(R.id.home_mgr_pro_ae_done).setOnClickListener(this);
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        if(inited)
        {
            return;
        }
        if (null != order_id) {
            mInnProductUICallbacks.fetchProductInfo(order_id);
        }else{
            initAddView();
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
        mInnProductUICallbacks = (InnController.InnProductUICallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.pro_price_deadline_value == v.getId()) {
            showDateTime();
        } else if (R.id.pro_info_label_value == v.getId()) {
            showTags();
        } else if (R.id.home_mgr_pro_ae_cancel == v.getId()) {
            ((BaseTourActivity) this.getActivity()).getDisplay().hideHomeSecondContent();
        } else if (R.id.pro_info_type_value == v.getId()) {
            showTypes();
        }else if(R.id.home_mgr_pro_ae_done == v.getId() || R.id.home_mgr_pro_ae_bottom_done == v.getId()){
            if(0 == picsAdapter.getLocalFiles().size()){
                updateProduct("");
            }else{
                mInnProductUICallbacks.uploadProImgs(picsAdapter.getLocalFiles());
            }
        }


    }



    private void showDateTime() {
        if (null == dateTimeDialog) {
            dateTimeDialog = new DateTimeDialog(this.getActivity(), R.style.translucent);
            dateTimeDialog.setTargetView(((TextView) content.findViewById(R.id.pro_price_deadline_value)));
        }
        dateTimeDialog.show();
    }

    private void showTags() {
        if (null == tagsDialog) {
            tagsDialog = new ProductTagsDialog(this.getActivity(), R.style.translucent);
            tagsDialog.setTagShow((TextView) content.findViewById(R.id.pro_info_label_value));
        }
        tagsDialog.show();
    }

    private void showTypes() {
        if (null == typeDialog) {
            typeDialog = new ProTypeChooseDialog(this.getActivity(), R.style.translucent);
            typeDialog.setTagShow((TextView) content.findViewById(R.id.pro_info_type_value));
        }
        typeDialog.show();
    }


    @Override
    public void initEditView(ProductInfo info) {
        inited = true;
        currentInfo = info;
        ((TextView) content.findViewById(R.id.home_mgr_pro_ae_title)).setText(R.string.home_mgr_pro_ae_edit);
        ((TextView) content.findViewById(R.id.pro_info_name_value)).setText(info.product_name);
        ((TextView) content.findViewById(R.id.pro_info_label_value)).setText(info.tag_name);
        ((TextView) content.findViewById(R.id.pro_info_type_value)).setText(info.category);
        ((TextView) content.findViewById(R.id.pro_old_price_value)).setText(this.getString(R.string.home_mgr_pro_ae_price_str, info.old_price));
        ((TextView) content.findViewById(R.id.pro_selling_price_value)).setText(this.getString(R.string.home_mgr_pro_ae_price_str, info.price));
        ((TextView) content.findViewById(R.id.pro_quantity_value)).setText(info.quantity);

        ((TextView) content.findViewById(R.id.pro_price_deadline_value)).setText(DateUtil.formatDate(Long.valueOf(info.tuan_end_time + "000")));
        ((TextView) content.findViewById(R.id.pro_intro_parchase_value)).setText(info.booking_info);
        ((TextView) content.findViewById(R.id.pro_intro_intro_value)).setText(info.note);
        ((TextView) content.findViewById(R.id.pro_recommend_words_vaule)).setText(info.content);
        ((TextView) content.findViewById(R.id.pro_recommend_travel_comment_value)).setText(info.traveler);
        ((TextView) content.findViewById(R.id.pro_recommend_boss_comment_value)).setText(info.innholder);
        ((KeywordsLinearLayout) content.findViewById(R.id.pro_recommend_keywords_vaule)).addKeyWords(info.keyword);
        picsAdapter.setStrData(info.product_images);
    }

    @Override
    public void initAddView() {
        currentInfo = new ProductInfo();
        ((TextView) content.findViewById(R.id.home_mgr_pro_ae_title)).setText(R.string.home_mgr_pro_ae_add);
    }



    private boolean isSubmitable() {
        String product_name =
                ((TextView) content.findViewById(R.id.pro_info_name_value)).getText().toString();
        String old_price = ((TextView) content.findViewById(R.id.pro_old_price_value)).getText().toString();
        String price = ((TextView) content.findViewById(R.id.pro_selling_price_value)).getText().toString();
        String quantity = ((TextView) content.findViewById(R.id.pro_quantity_value)).getText().toString();

        String tuan_end_time = ((TextView) content.findViewById(R.id.pro_price_deadline_value)).getText().toString();
        String booking_info = ((TextView) content.findViewById(R.id.pro_intro_parchase_value)).getText().toString();
        String note = ((TextView) content.findViewById(R.id.pro_intro_intro_value)).getText().toString();
        String contentValue = ((TextView) content.findViewById(R.id.pro_recommend_words_vaule)).getText().toString();
        String traveler = ((TextView) content.findViewById(R.id.pro_recommend_travel_comment_value)).getText().toString();
        String innholder = ((TextView) content.findViewById(R.id.pro_recommend_boss_comment_value)).getText().toString();
        String keyword = ((KeywordsLinearLayout) content.findViewById(R.id.pro_recommend_keywords_vaule)).getKeyWords();

        if (picsAdapter.getCount() == 0) {
            ToastUtil.showShortToast(this.getString(R.string.home_mgr_pro_ae_need_pics));
            return false;
        }

        if(TourStringUtil.isNULLorEmpty(product_name)){

        }

        return true;
    }

    @Override
    public void updateProduct(String addPics) {
        String product_name =
                ((TextView) content.findViewById(R.id.pro_info_name_value)).getText().toString();
        String old_price = ((TextView) content.findViewById(R.id.pro_old_price_value)).getText().toString();
        String price = ((TextView) content.findViewById(R.id.pro_selling_price_value)).getText().toString();
        String quantity = ((TextView) content.findViewById(R.id.pro_quantity_value)).getText().toString();

        String tuan_end_time = ((TextView) content.findViewById(R.id.pro_price_deadline_value)).getText().toString();
        String booking_info = ((TextView) content.findViewById(R.id.pro_intro_parchase_value)).getText().toString();
        String note = ((TextView) content.findViewById(R.id.pro_intro_intro_value)).getText().toString();
        String contentValue = ((TextView) content.findViewById(R.id.pro_recommend_words_vaule)).getText().toString();
        String traveler = ((TextView) content.findViewById(R.id.pro_recommend_travel_comment_value)).getText().toString();
        String innholder = ((TextView) content.findViewById(R.id.pro_recommend_boss_comment_value)).getText().toString();
        String keyword = ((KeywordsLinearLayout) content.findViewById(R.id.pro_recommend_keywords_vaule)).getKeyWords();

        ProductParams params = new ProductParams();

        params.product_images = TourStringUtil.isNULLorEmpty(addPics)?picsAdapter.getIntenetUrls():picsAdapter.getIntenetUrls()+","+addPics;
        params.editProduct = "";
        params.ccid = null == typeDialog || null == typeDialog.getCuList() ? currentInfo.category_id : typeDialog.getCuList().getCategory_id();
        params.cid = null == typeDialog || null == typeDialog.getCuList() ? currentInfo.category : typeDialog.getCuList().getCategory();
        params.tag_id = null == tagsDialog || null == tagsDialog.getChosedTag() ? currentInfo.tag_id : tagsDialog.getChosedTag().getTag_id();

        params.tuan_end_time = null == dateTimeDialog || null == dateTimeDialog.getCurrent() ?
                String.valueOf(DateUtil.getDate(tuan_end_time).getTime() / 1000) : String.valueOf(dateTimeDialog.getCurrent().getTime() / 1000);

        params.booking_info =booking_info;
        params.note =note;
        params.traveler =traveler;
        params.content =contentValue;
        params.innholder =innholder;
        params.product_name =product_name;
        params.keyword =keyword;
        params.price =price.replace(this.getString(R.string.rmb_unit),"");
        params.old_price =old_price.replace(this.getString(R.string.rmb_unit),"");
        params.quantity = quantity;
        params.item = currentInfo.product_id;
        if( null != order_id){
            mInnProductUICallbacks.updateProductInfo(params);
        }else{
            mInnProductUICallbacks.addProduct(params);
        }

    }

    public void showLocalImg(Uri uri){
        picsAdapter.addLocalPicItem(uri);
    }

    public void showProductAddSuccessed(){
        ToastUtil.showShortToast(this.getString(R.string.home_mgr_pro_ae_add_success));
    }

    public void showProductEditSuccessed(){
        ToastUtil.showShortToast(this.getString(R.string.home_mgr_pro_ae_edit_success));
    }

}
