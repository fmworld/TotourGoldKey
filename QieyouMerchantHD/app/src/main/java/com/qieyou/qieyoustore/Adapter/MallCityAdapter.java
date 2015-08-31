package com.qieyou.qieyoustore.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.LocalTitle;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.util.ViewHolderHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class MallCityAdapter extends MallFilterAdapter {
    private final String city_tag ="mall_city_tag";


    public MallCityAdapter(Context mContext) {
        super(mContext);
        currentIndex = Integer.valueOf(TourApplication.instance().getDaoProperty().getValue(city_tag,"0"));
    }


    public void onItemClick(int index) {
        currentIndex = index;
        TourApplication.instance().getDaoProperty()
                .saveProperty(city_tag, String.valueOf(currentIndex));
        this.notifyDataSetInvalidated();
    }


    private int getCurrentIndex(String tag_id){
        int size = items.size();
        for(int i =0; i < size; i++){
            if(items.get(i).getDest_id().equals(tag_id)){
                return i;
            }
        }
        return 0;
    }
}
