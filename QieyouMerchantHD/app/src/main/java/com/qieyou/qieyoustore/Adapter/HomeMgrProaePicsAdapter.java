package com.qieyou.qieyoustore.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.fm.fmlib.tour.TourConfig;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.bean.HomeMgrProPicItem;
import com.qieyou.qieyoustore.util.TourStringUtil;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeMgrProaePicsAdapter extends BaseAdapter implements View.OnClickListener {
    List<HomeMgrProPicItem> items;
    int currentIndex = 0;
    private Context mContext;

    public HomeMgrProaePicsAdapter(Context mContext) {
        this.mContext = mContext;
        items = new ArrayList<HomeMgrProPicItem>();
        items.add(new HomeMgrProPicItem());
//        initData();
    }

    public void setStrData(String pics) {
        if (TourStringUtil.isNULLorEmpty(pics)) {
            return;
        }
        HomeMgrProPicItem ppItem;
        String[] picArr = pics.split(",");
        items.clear();
        for (String url : picArr) {
            ppItem = new HomeMgrProPicItem();
            ppItem.local = false;
            ppItem.uri = Uri.parse(TourConfig.instance().getImageRoot() + "/" + url);
            items.add(ppItem);
        }
        items.add(new HomeMgrProPicItem());
        this.notifyDataSetInvalidated();
    }

    public void addLocalPicItem(Uri uri) {
        HomeMgrProPicItem ppItem = new HomeMgrProPicItem();
        ppItem.local = true;
        ppItem.uri = uri;
        items.add(this.getCount() - 1, ppItem);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return null == items ? 0 : items.size() < 32 ? items.size() : 31;
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

        HomeMgrProPicsHolderHelper holderHelper;
        holderHelper = HomeMgrProPicsHolderHelper.get(mContext, position, convertView, parent, R.layout.grid_item_pro_pic);
        if (position == getCount() - 1) {
            holderHelper.setVisibility(R.id.home_mgr_pro_pic_delete_icon, View.GONE);
            holderHelper.setPlaceHoldImg(R.id.home_mgr_pro_pic, R.drawable.home_mgr_pro_add_pic);
            holderHelper.setClickListener(R.id.home_mgr_pro_pic, this, position);
        } else {
            HomeMgrProPicItem item = items.get(position);
            holderHelper.setVisibility(R.id.home_mgr_pro_pic_delete_icon, View.VISIBLE);
            holderHelper.setImageURI(R.id.home_mgr_pro_pic, item.uri);
            holderHelper.setClickListener(R.id.home_mgr_pro_pic_delete_icon, this, position);
        }

        return holderHelper.mConvertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (R.id.home_mgr_pro_pic_delete_icon == v.getId()&&position < getCount() - 1) {
                items.remove(position);
                this.notifyDataSetChanged();
        } else if (R.id.home_mgr_pro_pic == v.getId() && position == getCount() - 1) {
            showPhotoActionSheetDialog();
        }
    }

    public void showPhotoActionSheetDialog() {
        final Dialog mDialog = new Dialog(mContext, R.style.ActionSheet);
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.widget_pic_choose_type_list, null);
        layout.findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((BaseTourActivity)mContext).selectPicFromCamera();
                mDialog.dismiss();
            }
        });
        layout.findViewById(R.id.btn_0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((BaseTourActivity)mContext).selectPicFromLocal();
                mDialog.dismiss();
            }
        });
        layout.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
            }
        });

        Window mWindow = mDialog.getWindow();
        WindowManager.LayoutParams mLayoutParams = mWindow.getAttributes();
        mLayoutParams.x = 0;
        final int cMakeBottom = -1000;
        mLayoutParams.y = cMakeBottom;
        mLayoutParams.gravity = Gravity.BOTTOM;
        mDialog.onWindowAttributesChanged(mLayoutParams);
        mDialog.setCanceledOnTouchOutside(true);

        mDialog.setContentView(layout);
        mDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mDialog.show();
    }

    public String getIntenetUrls(){
        StringBuffer sb = new StringBuffer();
        String temp;
        for(HomeMgrProPicItem item : items){
            if(!item.local &&null != item.uri){
                temp = item.uri.toString();
                sb.append(temp.substring(TourConfig.instance().getImageRoot().length()+1,temp.length())).append(",");
            }
        }
        int index = sb.lastIndexOf(",");
        if(index > 0){
            return sb.substring(0,index);
        }
        return "";
    }

    public List<Uri> getLocalFiles(){
        List<Uri> localUris = new ArrayList<>();
        for(HomeMgrProPicItem item : items){
            if(item.local &&null != item.uri){
                localUris.add(item.uri);
            }
        }
        return localUris;
    }
}
