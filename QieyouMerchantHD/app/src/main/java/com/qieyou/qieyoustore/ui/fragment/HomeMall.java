package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.Product;
import com.fm.fmlib.state.ProductState;
import com.fm.fmlib.utils.DisplayUtil;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liangfeizc.RubberIndicator;
import com.qieyou.qieyoustore.Adapter.HomeNavigationAdapter;
import com.qieyou.qieyoustore.Adapter.MallAddAdapter;
import com.qieyou.qieyoustore.Adapter.MallProductAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.ui.widget.MallCITabBar;
import com.qieyou.qieyoustore.ui.widget.MallFilterPopWindow;
import com.qieyou.qieyoustore.ui.widget.MallTabBar;
import com.qieyou.qieyoustore.ui.widget.TourWaveRefeshLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.LogRecord;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeMall extends AnimListenFragment implements ProductController.MallUi, View.OnClickListener, MallTabBar.MTBItemClickListener, MallFilterPopWindow.FilterListener,WaveSwipeRefreshLayout.OnRefreshListener {
    ProductController.ProductMallCallbacks mallCallbacks;
    private MallTabBar mtBar;
    private MallCITabBar mtCIBar;
    private MallFilterPopWindow filterPopWindow;
    private Button filter;
    private MallProductAdapter productAdapter;
    private View contentView;
    private TourWaveRefeshLayout refreshLayout;
    private Timer addTimer;
    private ViewPager adds;

    Handler handler = new Handler() {
       public void handleMessage(Message msg){

       }
    };
    public HomeMall() {
    }

    public static HomeMall create() {
        HomeMall fragment = new HomeMall();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_home_mall, null);
        mtBar = (MallTabBar) contentView.findViewById(R.id.mall_category_list);
        mtBar.setOnMTBClickListener(this);
        mtCIBar = (MallCITabBar) contentView.findViewById(R.id.mall_category_id_list);
        mtCIBar.setOnMTBClickListener(this);
        filter = (Button)contentView.findViewById(R.id.mall_filter);
        filter.setOnClickListener(this);

        refreshLayout = (TourWaveRefeshLayout)contentView.findViewById(R.id.mall_wave_refresh);
        refreshLayout.setColorSchemeColors(this.getResources().getColor(R.color.white), this.getResources().getColor(R.color.red), this.getResources().getColor(R.color.yellow));
        refreshLayout.setWaveColor(this.getResources().getColor(R.color.green));

        refreshLayout.setMaxDropHeight(DisplayUtil.dip2px(this.getActivity(), 150));
        refreshLayout.setOnRefreshListener(this);
        productAdapter = new MallProductAdapter(this.getActivity());
        final PullToRefreshListView refreshListView = ((PullToRefreshListView)contentView.findViewById(R.id.mall_pro_list));
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setAdapter(productAdapter);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.v("list refresh", "onRefresh");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshListView.onRefreshComplete();
                    }
                }, 3000);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.v("list refresh", "onRefresh");
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshListView.onRefreshComplete();
                    }
                }, 3000);
            }
        });
        refreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product pro = (Product) productAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString("item", pro.getProduct_id());
                ((BaseTourActivity) (HomeMall.this.getActivity()))
                        .getDisplay()
                        .showHomeSecondContent(MainController.HomeMenu.PRO_DETAIL, bundle);
            }
        });

        this.setAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if(null != mallCallbacks){
                    initAdds(contentView);
                    mtBar.setData(TourApplication.instance().getDaoCategory().getCategoryTitles());
                    initAddTasks();
                }
            }
        });

        return contentView;
    }

    private void initAdds(View view){
        MallAddAdapter addAdapter = new MallAddAdapter(this.getActivity());

        adds = (ViewPager)view.findViewById(R.id.mall_add_container);
        List<LaunchProfile> profiles = TourApplication.instance().getDaoLaunProfile()
                .getLaunProfiles(ProductState.LaunchProfileType.store.toString());
        addAdapter.setdata(profiles);
        adds.setAdapter(addAdapter);
        final RubberIndicator indicator = (RubberIndicator)view.findViewById(R.id.mall_add_indicator);
        indicator.setCount(null == profiles ? 0 : profiles.size());
        adds.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setFocusPosition(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        if(null != addTimer){
            initAddTasks();
        }
    }

    private void initAddTasks(){
        addTimer = new Timer();
        addTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(null != adds){
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adds.setCurrentItem(adds.getCurrentItem() <
                                    adds.getAdapter().getCount()-1?adds.getCurrentItem()+1:0);
                        }
                    });

                }
            }
        },3000,3000);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        if(null != addTimer){
            addTimer.cancel();
        }

        super.onPause();
    }

    ProductController getController() {
        return MerchanthdApplication.instance().getmMainController().getProductController();
    }

    @Override
    public void setCallbacks(ProductController.ProductUiCallbacks callbacks) {
        mallCallbacks = (ProductController.ProductMallCallbacks) callbacks;
    }

    @Override
    public boolean isModal() {
        return false;
    }

    @Override
    public void onClick(View v) {
        if (R.id.mall_filter == v.getId()) {
            if (null == filterPopWindow) {
                filterPopWindow = new MallFilterPopWindow(this.getActivity());
                filterPopWindow.initView();
                filterPopWindow.setTarText((TextView)contentView.findViewById(R.id.mall_filter));
                filterPopWindow.setFilterListener(this);
            }
            if (!filterPopWindow.isShowing()) {

                filterPopWindow.showAsDropDown(filter);
            }
        }
    }

    @Override
    public void itemClicked(View view, String ca_id) {
        if (mtBar == view) {
            if (null != mtCIBar) {
                mtCIBar.setData(TourApplication.instance().getDaoCategory().getCategoryLists(ca_id));
            }
            return;
        }

        if (mtCIBar == view) {
            fetchProducts();
            return;
        }
    }

    private void fetchProducts(){
        mallCallbacks.fetchProducts("0","20","530700",
                mtBar.getSelectedItem(),mtCIBar.getSelectedItem(),
                null ==filterPopWindow?"":filterPopWindow.getLocalDest(),
                null ==filterPopWindow?"time":filterPopWindow.getSortType());
    }
    @Override
    public void updateTabBar() {

    }

    @Override
    public void refreshProductList(List<Product> products) {
       if(null != refreshLayout&&refreshLayout.isRefreshing()){
           refreshLayout.setRefreshing(false);
       }
        productAdapter.setdata(products);

    }

    @Override
    public void choosedFilter(String local_id, String sort) {
        fetchProducts();
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchProducts();
            }
        }, 1000);

    }
}
