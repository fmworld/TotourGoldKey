package com.qieyou.qieyoustore.ui.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
public class HomeCodeInput extends AnimListenFragment implements ProductController.CodeUi, View.OnClickListener, TextWatcher {
    private ProductController.ProductCodeCallbacks mProductCodeCallbacks;
    private TextView homeCodeValue;
    private ImageView homeCodeDelete;
    private Button homeCodeSubmit;
    public HomeCodeInput() {

    }

    public static HomeCodeInput create() {
        HomeCodeInput fragment = new HomeCodeInput();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_code, null);
        (view.findViewById(R.id.code_close)).setOnClickListener(this);
        homeCodeDelete =  (ImageView)view.findViewById(R.id.home_code_delete);
        homeCodeDelete.setOnClickListener(this);
        homeCodeValue =  (TextView)view.findViewById(R.id.home_code_value);
        homeCodeValue.addTextChangedListener(this);

        homeCodeSubmit = (Button)view.findViewById(R.id.home_code_submit);
        homeCodeSubmit.setOnClickListener(this);
        (view.findViewById(R.id.home_code_num1)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num1)).setTag("1");
        (view.findViewById(R.id.home_code_num2)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num2)).setTag("2");
        (view.findViewById(R.id.home_code_num3)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num3)).setTag("3");
        (view.findViewById(R.id.home_code_num4)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num4)).setTag("4");
        (view.findViewById(R.id.home_code_num5)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num5)).setTag("5");
        (view.findViewById(R.id.home_code_num6)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num6)).setTag("6");
        (view.findViewById(R.id.home_code_num7)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num7)).setTag("7");
        (view.findViewById(R.id.home_code_num8)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num8)).setTag("8");
        (view.findViewById(R.id.home_code_num9)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num9)).setTag("9");
        (view.findViewById(R.id.home_code_num0)).setOnClickListener(this);
        (view.findViewById(R.id.home_code_num0)).setTag("0");
        return view;
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
        mProductCodeCallbacks = (ProductController.ProductCodeCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.code_close == v.getId()) {
            this.getActivity().finish();
        }else if(R.id.home_code_delete == v.getId()){
            String value = homeCodeValue.getText().toString();
            if(!TourStringUtil.isNULLorEmpty(value)){
                value = value.substring(0,value.length()-1);
                homeCodeValue.setText(value);
            }
        }else if(R.id.home_code_submit == v.getId()){
            mProductCodeCallbacks.verifyCode(homeCodeValue.getText().toString());
        }else{
            String value = homeCodeValue.getText().toString();
            value += v.getTag();
            homeCodeValue.setText(value);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        homeCodeSubmit.setBackgroundResource(s.length() ==14
                    ?R.drawable.home_code_submit_can_bg:R.drawable.home_code_submit_unable_bg);
        homeCodeSubmit.setClickable(s.length() ==14);
    }

    @Override
    public void updateTabBar() {

    }

    @Override
    public void showBadCodeMsg(String msg) {
//        homeCodeValue.setText("");
//        homeCodeValue.setHint(this.getString(R.string.code_verify_input_bad));
        Bundle bundle = new Bundle();
        bundle.putString("name", "霸气单打");
        bundle.putString("price", "36.00");
        bundle.putString("old_price", "878.00");
        bundle.putString("quantity","12");
        ((BaseTourActivity)getActivity()).getDisplay().showVerifyCode(MainController.HomeMenu.VERIFY_SUCCESS, bundle);
    }

    @Override
    public void codeSuccessed(CodeInfo codeInfo) {
        Bundle bundle = new Bundle();
        bundle.putString("name", codeInfo.product);
        bundle.putString("price", codeInfo.price);
        bundle.putString("old_price", codeInfo.old_price);
        bundle.putString("quantity", codeInfo.quantity);
        ((BaseTourActivity)getActivity()).getDisplay().showVerifyCode(MainController.HomeMenu.VERIFY_SUCCESS, bundle);
    }
}
