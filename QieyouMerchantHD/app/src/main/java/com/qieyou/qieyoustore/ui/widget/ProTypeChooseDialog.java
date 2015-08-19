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
import com.fm.fmlib.dao.CategoryList;
import com.fm.fmlib.dao.CategoryTitle;
import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.Adapter.ProCategoryAdapter;
import com.qieyou.qieyoustore.Adapter.ProCategoryIDAdapter;
import com.qieyou.qieyoustore.Adapter.ProTagListAdapter;
import com.qieyou.qieyoustore.NewTagActivity;
import com.qieyou.qieyoustore.R;

/**
 * Created by zhoufeng'an on 2015/8/17.
 */
public class ProTypeChooseDialog extends Dialog implements View.OnClickListener{
    private  TextView tagShow;
    private ProCategoryAdapter pcAdapter;
    private ProCategoryIDAdapter pcIdAdapter;
    private CategoryTitle cuTitle;
    private CategoryList cuList;
    public ProTypeChooseDialog(Context context) {
        super(context);
        initView();
    }

    public ProTypeChooseDialog(Context context, int theme) {
        super(context, theme);
        initView();
    }

    protected ProTypeChooseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initView();
    }

    private void initView() {
        ActionBar.LayoutParams prams = new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        prams.gravity = Gravity.CENTER;
        View view = View.inflate(this.getContext(), R.layout.widget_pro_type_choose,null);
        pcAdapter = new ProCategoryAdapter(this.getContext());
        pcAdapter.setdata(TourApplication.instance().getDaoCategory().getCategoryTitles());
        ((ListView)view.findViewById(R.id.pro_category_list)).setAdapter(pcAdapter);
        ((ListView)view.findViewById(R.id.pro_category_list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pcAdapter.onItemClick(position);
                pcIdAdapter.setdata(TourApplication.instance().getDaoCategory().getCategoryLists(pcAdapter.getCurrentTag().getId()));
            }
        });

        pcIdAdapter = new ProCategoryIDAdapter(this.getContext());
        pcIdAdapter.setdata(TourApplication.instance().getDaoCategory().getCategoryLists(pcAdapter.getCurrentTag().getId()));
        ((ListView)view.findViewById(R.id.pro_category_id_list)).setAdapter(pcIdAdapter);
        ((ListView)view.findViewById(R.id.pro_category_id_list)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pcIdAdapter.onItemClick(position);
            }
        });

        ((View)view.findViewById(R.id.pro_tag_new_cancel)).setOnClickListener(this);
        ((View)view.findViewById(R.id.pro_tag_new_complete)).setOnClickListener(this);

        addContentView(view, prams);

        Log.v("show date", "onClick");
    }

    public void setTagShow(TextView tagShow) {
        this.tagShow = tagShow;
    }

    @Override
    public void onClick(View v) {
        if(R.id.pro_tag_new_cancel == v.getId()){
            this.dismiss();
            return;
        }

        if(R.id.tag_list_add_layout == v.getId()){
            this.getContext().startActivity(new Intent(getContext(), NewTagActivity.class));
            return;
        }

        if(R.id.pro_tag_new_complete == v.getId()){
            if(null != tagShow){
                cuTitle =pcAdapter.getCurrentTag();
                cuList = pcIdAdapter.getCurrentTag();

                if(null !=cuTitle &&null != cuList){
                    tagShow.setText(this.getContext().getString(R.string.home_mgr_pro_type_str,cuTitle.getName(),cuList.getName()));
                }
            }
            dismiss();
            return;
        }
    }

    public CategoryList getCuList() {
        return cuList;
    }

    public CategoryTitle getCuTitle() {
        return cuTitle;
    }
}
