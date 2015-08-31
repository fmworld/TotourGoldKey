package com.qieyou.qieyoustore.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.LocalTitle;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.Adapter.MallCityAdapter;
import com.qieyou.qieyoustore.Adapter.MallFilterAdapter;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.bean.HomeMallCityItem;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/21.
 */
public class MallCityDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener {
    private List<LocalTitle> cities;
private View mContent;
    public interface CityChooseListener {
        void choosedCity(String city_name, String city_id);
    }

    private ListView cityChooser;
    private Context mContext;
    private TextView tarText;
    private MallCityAdapter cityAdapter;
    private CityChooseListener filterListener;

    public MallCityDialog(Context context) {
        super(context, R.style.deep_translucent);
        mContext = context;
        cities = new ArrayList<>();
        LocalTitle item = new LocalTitle();
        item.setDest_id("530700");
        item.setDest_name("丽江");
        cities.add(item);

        item = new LocalTitle();
        item.setDest_id("532900");
        item.setDest_name("大理");
        cities.add(item);

        cityAdapter = new MallCityAdapter(mContext);
        cityAdapter.setdata(cities);
        initView();
    }

    private void initView() {
        mContent = View.inflate(mContext, R.layout.widget_mall_city_filter, null);
        cityChooser = (ListView) mContent.findViewById(R.id.mall_city_filter);
        mContent.setOnClickListener(this);
        cityChooser.setAdapter(cityAdapter);
        cityChooser.setOnItemClickListener(this);
        this.setContentView(mContent);
//        this.setBackgroundDrawable(new BitmapDrawable());
//        this.setOutsideTouchable(true);

    }

    public LocalTitle getSelecteCity() {
        if (null == cityAdapter) {
            return null;
        }

        return cityAdapter.getCurrentTag();
    }

    public String initTarText(TextView tarText) {
        if (null != tarText) {
            this.tarText = tarText;
            this.tarText.setText(getSelecteCity().getDest_name());
        }
        return getSelecteCity().getDest_id();
    }

    public void setCityChooseListener(CityChooseListener filterListener) {
        this.filterListener = filterListener;
    }

    @Override
    public void onClick(View v) {
        if(v == mContent){
            this.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cityAdapter.onItemClick(position);
        if (null != filterListener) {
            filterListener.choosedCity(cities.get(position).getDest_name(), cities.get(position).getDest_id());
        }

        if (null != tarText) {
            tarText.setText(cities.get(position).getDest_name());
        }

        this.dismiss();
    }

    public void showAt(int x, int y){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)cityChooser.getLayoutParams();
        params.leftMargin =x;
        params.topMargin = y;
        show();
    }
}
