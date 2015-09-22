package com.qieyou.qieyoustore.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.dao.LocalTitle;
import com.fm.fmlib.utils.DisplayUtil;
import com.qieyou.qieyoustore.Adapter.MallFilterAdapter;
import com.qieyou.qieyoustore.R;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/21.
 * 商城，分类筛选
 */
public class MallFilterDialog extends Dialog implements View.OnClickListener, AdapterView.OnItemClickListener{
    private View mContent;

    public interface FilterListener{
        void choosedFilter(String local_id, String sort);
    }
    private ListView localTitle;
    private ListView sortType;
    private Context mContext;
    private TextView tarText;
    private MallFilterAdapter localAdapter;
    private MallFilterAdapter sortAdapter;
    private FilterListener filterListener;
    public MallFilterDialog(Context context){
        super(context, R.style.deep_translucent);
        mContext = context;

    }
    public void setPopupWindowTouchModal(PopupWindow popupWindow,
                                                boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {

            method = PopupWindow.class.getDeclaredMethod("setTouchModal",
                    boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);

        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void initView(){
        mContent = View.inflate(mContext, R.layout.widget_mall_filter, null);
        localAdapter = new MallFilterAdapter(mContext);
        localTitle = (ListView)mContent.findViewById(R.id.mall_filter_local_title);
        localTitle.setAdapter(localAdapter);
        localTitle.setOnItemClickListener(this);
        List<LocalTitle> locals = TourApplication.instance().getDaoLocal().getLocalTitles();
        LocalTitle title = new LocalTitle();
        title.setDest_name(mContext.getString(R.string.mall_filter_local_all));
        title.setDest_id("");
        locals.add(0, title);
        localAdapter.setdata(locals);
        sortAdapter = new MallFilterAdapter(mContext);
        sortType = (ListView)mContent.findViewById(R.id.mall_filter_sort_type);
        sortType.setAdapter(sortAdapter);
        sortType.setOnItemClickListener(this);
        sortAdapter.setdata(initSortType());
        mContent.findViewById(R.id.mall_filter_complete).setOnClickListener(this);

        this.setContentView(mContent);
        mContent.setOnClickListener(this);
//        this.setWidth(DisplayUtil.dip2px(this.mContext, 382));
//        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
//        this.setBackgroundDrawable(new BitmapDrawable());
//        this.setOutsideTouchable(true);

    }

    public void setTarText(TextView tarText) {
        this.tarText = tarText;
    }

    private List<LocalTitle> initSortType(){
        List<LocalTitle> items = new ArrayList<LocalTitle>();
        LocalTitle title;

        title = new LocalTitle();
        title.setDest_name(mContext.getString(R.string.mall_filter_sort_default));
        title.setDest_id("time");
        items.add(title);

        title = new LocalTitle();
        title.setDest_name(mContext.getString(R.string.mall_filter_sort_popular));
        title.setDest_id("highp");
        items.add(title);

        title = new LocalTitle();
        title.setDest_name(mContext.getString(R.string.mall_filter_sort_tip));
        title.setDest_id("agent");
        items.add(title);

        title = new LocalTitle();
        title.setDest_name(mContext.getString(R.string.mall_filter_sale));
        title.setDest_id("lowp");
        items.add(title);

        title = new LocalTitle();
        title.setDest_name(mContext.getString(R.string.mall_filter_dis));
        title.setDest_id("local");
        items.add(title);

        title = new LocalTitle();
        title.setDest_name(mContext.getString(R.string.mall_filter_new));
        title.setDest_id("time");
        items.add(title);
        return  items;
    }

    public void setFilterListener(FilterListener filterListener) {
        this.filterListener = filterListener;
    }

    @Override
    public void onClick(View v) {
        if(R.id.mall_filter_complete == v.getId()){
            LocalTitle title = localAdapter.getCurrentTag();
            LocalTitle sort = sortAdapter.getCurrentTag();
            if(null != tarText){
                tarText.setText(title.getDest_name()+"/"+sort.getDest_name());
            }
            if(null != filterListener){
                filterListener.choosedFilter(title.getDest_id(),sort.getDest_id());
            }
            this.dismiss();
        }else if(v == mContent){
            this.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MallFilterAdapter adapter =  localTitle == parent?localAdapter:sortAdapter;
        adapter.onItemClick(position);
    }

    public String getLocalDest(){
        return localAdapter.getCurrentTag().getDest_id();
    }

    public String getSortType(){
        return sortAdapter.getCurrentTag().getDest_id();
    }

    public void showAt(int x, int y){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                mContent.findViewById(R.id.filter_layout).getLayoutParams();
        params.leftMargin =x;
        params.topMargin = y;
        this.show();
    }

    public void resetIndex(){
        if(null != sortAdapter){
            sortAdapter.resetIndex();
        }
        if(null != localAdapter){
            localAdapter.resetIndex();
        }

        LocalTitle title = localAdapter.getCurrentTag();
        LocalTitle sort = sortAdapter.getCurrentTag();
        if(null != tarText){
            tarText.setText(title.getDest_name() + "/" + sort.getDest_name());
        }
    }

    public void showAtRight(int x, int y){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)
                mContent.findViewById(R.id.filter_layout).getLayoutParams();
        params.rightMargin =x;
        params.topMargin = y;
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        this.show();
    }
}
