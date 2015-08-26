package com.qieyou.qieyoustore;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

import com.fm.fmlib.Display;
import com.qieyou.qieyoustore.util.ToastUtil;
import com.qieyou.qieyoustore.util.TourStringUtil;
import com.qieyou.qieyoustore.util.TourWebViewClient;

/**
 * Created by zhoufeng'an on 2015/8/12.
 */
public class NewTagActivity extends BaseTourActivity implements View.OnClickListener{
    protected int getContentViewLayoutId() {
        return R.layout.activity_new_tag;
    }

    protected void handleIntent(Intent intent, Display display) {
        findViewById(R.id.tag_new_confirm).setFocusable(true);
        findViewById(R.id.tag_new_confirm).setFocusableInTouchMode(true);
        findViewById(R.id.tag_new_confirm).requestFocus();
        findViewById(R.id.tag_new_confirm).setOnClickListener(this);
        findViewById(R.id.pro_tag_new_close).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(R.id.tag_new_confirm == v.getId()){
            String value = ((EditText)findViewById(R.id.tag_new_value)).getText().toString();
            if(!TourStringUtil.isNULLorEmpty(value)){

            }else{
                ToastUtil.showShortToast(this.getString(R.string.home_mgr_pro_tag_new_notify));
            }
            return;
        }
        if(R.id.pro_tag_new_close == v.getId()){
           this.finish();
            return;
        }

    }
}