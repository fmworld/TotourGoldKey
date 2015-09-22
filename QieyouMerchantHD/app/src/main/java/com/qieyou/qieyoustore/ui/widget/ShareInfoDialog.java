package com.qieyou.qieyoustore.ui.widget;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.DateUtil;
import com.qieyou.qieyoustore.util.MyShareUtils;

import java.util.Date;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class ShareInfoDialog extends Dialog implements View.OnClickListener
{
    private MyShareUtils myShareUtils;
    private View content;
    private Activity mActivity;
//    private String title;
//    private String shareContent;
//    private String targetUrl;
//    private String imgUrl;
    public ShareInfoDialog(Activity activity) {
        super(activity, R.style.deep_translucent);
        mActivity = activity;
        initView();
    }

    private void initView() {
        content = View.inflate(getContext(), R.layout.widget_share_chooser,null);
        ActionBar.LayoutParams prams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        prams.gravity = Gravity.CENTER;
        addContentView(content, prams);
        content.setOnClickListener(this);
        content.findViewById(R.id.share_weixin).setOnClickListener(this);
        content.findViewById(R.id.share_wx_f_circle).setOnClickListener(this);
        content.findViewById(R.id.share_sina_weibo).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.share_sina_weibo){
            myShareUtils.shareSina();
            return;
        }

        if(v.getId() == R.id.share_weixin){
            myShareUtils.shareWeixin();
            return;
        }

        if(v.getId() == R.id.share_wx_f_circle){
            myShareUtils.shareCircle();
            return;
        }

        if(v==content){
            this.dismiss();
            return;
        }
    }

    public void showShareItems(String title,String _content,String targetUrl,String imgUrl){
        this.show();
        if(null == myShareUtils){
            myShareUtils = new MyShareUtils(mActivity);
        }
        myShareUtils.initShareContent(title, _content, targetUrl);
    }
}
