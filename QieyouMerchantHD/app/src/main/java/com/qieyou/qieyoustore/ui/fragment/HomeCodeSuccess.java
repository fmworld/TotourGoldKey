package com.qieyou.qieyoustore.ui.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.tour.bean.CodeInfo;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.util.TourStringUtil;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeCodeSuccess extends AnimListenFragment implements View.OnClickListener{
    public HomeCodeSuccess() {

    }

    public static HomeCodeSuccess create() {
        HomeCodeSuccess fragment = new HomeCodeSuccess();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_code_success, null);
        (view.findViewById(R.id.code_success_back)).setOnClickListener(this);
        ((TextView)view.findViewById(R.id.code_old_price)).getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        Bundle bundle = this.getArguments();
        ((TextView)view.findViewById(R.id.code_verfy_pro_name))
                .setText(this.getString(R.string.code_verify_success_name,
                        bundle.get("name"), bundle.get("quantity")));
        ((TextView)view.findViewById(R.id.code_price))
                .setText((String)bundle.get("price"));
        ((TextView)view.findViewById(R.id.code_old_price))
                .setText(this.getString(R.string.code_verify_success_old_price,
                        bundle.get("old_price")));
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    ProductController getController() {
        return MerchanthdApplication.instance().getmMainController().getProductController();
    }


    @Override
    public void onClick(View v) {
        if (R.id.code_success_back == v.getId()) {
            ((BaseTourActivity)getActivity()).getDisplay().showVerifyCode(MainController.HomeMenu.CODE,null);
        }
    }


}
