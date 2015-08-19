package com.qieyou.qieyoustore.ui.widget;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.ProductTag;
import com.qieyou.qieyoustore.Adapter.ProTagListAdapter;
import com.qieyou.qieyoustore.NewTagActivity;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.DateUtil;

import java.util.Date;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class ProductTagsDialog extends Dialog implements View.OnClickListener{
    private  TextView tagShow;
    private  ProTagListAdapter tagList;
    private ProductTag currentTag;
    public ProductTagsDialog(Context context) {
        super(context);
        initView();
    }

    public ProductTagsDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    protected ProductTagsDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        ActionBar.LayoutParams prams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        prams.gravity = Gravity.CENTER;
        View view = View.inflate(this.getContext(), R.layout.widget_product_tag_choose,null);
        tagList = new ProTagListAdapter(this.getContext());
        tagList.setdata(TourApplication.instance().getDaoProductTag().getProductTags());
        ((ListView)view.findViewById(R.id.pro_tag_list)).setAdapter(tagList);
        ((ListView)view.findViewById(R.id.pro_tag_list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tagList.onItemClick(position);
            }
        });
        ((View)view.findViewById(R.id.pro_tag_list_close)).setOnClickListener(this);
        ((View)view.findViewById(R.id.tag_list_add_layout)).setOnClickListener(this);
        ((View)view.findViewById(R.id.pro_tag_confirm)).setOnClickListener(this);

        addContentView(view, prams);

        Log.v("show date", "onClick");
    }

    public void setTagShow(TextView tagShow) {
        this.tagShow = tagShow;
    }

    @Override
    public void onClick(View v) {
        if(R.id.pro_tag_list_close == v.getId()){
            this.dismiss();
            return;
        }

        if(R.id.tag_list_add_layout == v.getId()){
            this.getContext().startActivity(new Intent(getContext(), NewTagActivity.class));
            return;
        }

        if(R.id.pro_tag_confirm == v.getId()){
            if(null != tagShow){
                currentTag =tagList.getCurrentTag();
                if(null !=currentTag){
                    tagShow.setText(currentTag.getTag_name());
                }
            }
            dismiss();
            return;
        }
    }

    public ProductTag getChosedTag(){
        return currentTag;
    }
}
