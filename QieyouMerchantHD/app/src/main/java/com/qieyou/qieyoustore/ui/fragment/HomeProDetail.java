package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.tour.TourConfig;
import com.fm.fmlib.tour.bean.ProductDetail;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.Adapter.ProDetailRightAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.util.TourStringUtil;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeProDetail extends AnimListenFragment implements ProductController.DetailUi, View.OnClickListener {
    private ProductController.ProductDetailCallbacks mProductDetailCallbacks;
    private String item;
    private View content;
    private ProDetailRightAdapter rightAdapter;

    public HomeProDetail() {
    }

    public static HomeProDetail create() {
        HomeProDetail fragment = new HomeProDetail();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_detail, null);
        content.setOnClickListener(this);
        item = (String) this.getArguments().get("item");
        (content.findViewById(R.id.detail_back)).setOnClickListener(this);
        (content.findViewById(R.id.detail_share)).setOnClickListener(this);
        this.setAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (null != mProductDetailCallbacks) {
                    mProductDetailCallbacks.fetchDetail(item);
                }
            }
        });
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }


    ProductController getController() {
        return MerchanthdApplication.instance().getmMainController().getProductController();
    }


    @Override
    public void setCallbacks(ProductController.ProductUiCallbacks callbacks) {
        mProductDetailCallbacks = (ProductController.ProductDetailCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.detail_back == v.getId()) {
            ((BaseTourActivity) this.getActivity()).getDisplay().hideHomeSecondContent();
        } else if (R.id.detail_share == v.getId()) {
            mProductDetailCallbacks.fetchShareInfo(item);
        }
    }


    @Override
    public void updateTabBar() {

    }

    @Override
    public void refreshProDetail(ProductDetail detail) {
        ((TextView) content.findViewById(R.id.pro_detail_title)).setText(detail.product_name);
        ((TextView) content.findViewById(R.id.detail_view_number)).setText(this.getString(R.string.pro_detail_view_str, detail.hit));
        ((TextView) content.findViewById(R.id.detail_buy_number)).setText(this.getString(R.string.pro_detail_view_str, detail.bought_count));
        ((TextView) content.findViewById(R.id.detail_detail_content)).setText(detail.note);
        ((TextView) content.findViewById(R.id.detail_attention_content)).setText(detail.booking_info);
        initImages((LinearLayout) content.findViewById(R.id.detail_top_imgs), detail.product_images);
        initImages((LinearLayout) content.findViewById(R.id.detail_bottom_imgs), detail.detail_images);
        rightAdapter = new ProDetailRightAdapter(this.getActivity(), detail);
        rightAdapter.setScroll(content.findViewById(R.id.detail_content_layout));
        ((ListView) content.findViewById(R.id.pro_detail_right)).setAdapter(rightAdapter);
    }

    @Override
    public void refreshStateChange(String id) {
        rightAdapter.notifyDataSetChanged();
    }

    private void initImages(LinearLayout layout, String imgstr) {
        if(TourStringUtil.isNULLorEmpty(imgstr)){
            return;
        }
        String[] imgs = imgstr.split(",");
        layout.removeAllViews();
        SimpleDraweeView view;
        for (String item : imgs) {
            view = initImageItem();
            layout.addView(view);
            view.setImageURI(Uri.parse(TourConfig.instance().getImageRoot() + "/" + item));
        }
    }

    private SimpleDraweeView initImageItem() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DisplayUtil.dip2px(this.getActivity(), 641), DisplayUtil.dip2px(this.getActivity(), 428));
        params.gravity = Gravity.CENTER;
        params.bottomMargin = DisplayUtil.dip2px(this.getActivity(), 15);
        SimpleDraweeView view = (SimpleDraweeView) View.inflate(this.getActivity(), R.layout.widget_simdrawee_view, null);
        view.setLayoutParams(params);
        return view;
    }
}
