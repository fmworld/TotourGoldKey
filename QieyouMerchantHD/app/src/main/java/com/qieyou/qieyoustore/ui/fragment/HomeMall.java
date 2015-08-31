package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.qieyou.qieyoustore.Adapter.MallAddAdapter;
import com.qieyou.qieyoustore.Adapter.MallProductAdapter;
import com.qieyou.qieyoustore.HomeAcitvity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.ui.widget.MallCITabBar;
import com.qieyou.qieyoustore.ui.widget.MallCityDialog;
import com.qieyou.qieyoustore.ui.widget.MallFilterDialog;
import com.qieyou.qieyoustore.ui.widget.MallTabBar;
import com.qieyou.qieyoustore.ui.widget.TourWaveRefeshLayout;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeMall extends AnimListenFragment implements ProductController.MallUi, View.OnClickListener,
        MallTabBar.MTBItemClickListener, MallFilterDialog.FilterListener,
        MallCityDialog.CityChooseListener, WaveSwipeRefreshLayout.OnRefreshListener {
    private final String PER_PAGE = "5";
    ProductController.ProductMallCallbacks mallCallbacks;
    private MallTabBar mtBar;
    private MallCITabBar mtCIBar;
    private MallFilterDialog filterPopWindow;
    private MallCityDialog cityChooser;
    private Button filter;
    private MallProductAdapter productAdapter;
    private View contentView;
    private TourWaveRefeshLayout refreshLayout;
    PullToRefreshListView refreshListView;
    private Timer addTimer;
    private ViewPager adds;
    private String CITY_ID = "";
    private int currentPage = 0;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

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
        filter = (Button) contentView.findViewById(R.id.mall_filter);
        filter.setOnClickListener(this);
        cityChooser = new MallCityDialog(this.getActivity());
        CITY_ID = cityChooser.initTarText((TextView) contentView.findViewById(R.id.mall_city_choose));
        cityChooser.setCityChooseListener(this);
        contentView.findViewById(R.id.mall_city_choose).setOnClickListener(this);
        refreshLayout = (TourWaveRefeshLayout) contentView.findViewById(R.id.mall_wave_refresh);
        refreshLayout.setColorSchemeColors(this.getResources().getColor(R.color.white), this.getResources().getColor(R.color.red), this.getResources().getColor(R.color.yellow));
        refreshLayout.setWaveColor(this.getResources().getColor(R.color.green));

        refreshLayout.setMaxDropHeight(DisplayUtil.dip2px(this.getActivity(), 150));
        refreshLayout.setOnRefreshListener(this);
        productAdapter = new MallProductAdapter(this.getActivity());
        refreshListView = ((PullToRefreshListView) contentView.findViewById(R.id.mall_pro_list));
        refreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        refreshListView.setAdapter(productAdapter);
        refreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                Log.v("list refresh", "onRefresh" + (currentPage + 1));
                fetchProducts(currentPage + 1);
            }
        });

        this.setAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (null != mallCallbacks) {
                    initAdds(contentView);
                    mtBar.setData(TourApplication.instance().getDaoCategory().getCategoryTitles());
                    initAddTasks();
                }
            }
        });

        return contentView;
    }

    private void initAdds(View view) {
        MallAddAdapter addAdapter = new MallAddAdapter(this.getActivity());

        adds = (ViewPager) view.findViewById(R.id.mall_add_container);
        List<LaunchProfile> profiles = TourApplication.instance().getDaoLaunProfile()
                .getLaunProfiles(ProductState.LaunchProfileType.store.toString());
        addAdapter.setdata(profiles);
        adds.setAdapter(addAdapter);
        final RubberIndicator indicator = (RubberIndicator) view.findViewById(R.id.mall_add_indicator);
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
        ((HomeAcitvity) getActivity()).selectNavigationItem(MainController.HomeMenu.MALL);
        if (null != addTimer) {
            initAddTasks();
        }
    }

    private void initAddTasks() {
        addTimer = new Timer();
        addTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (null != adds) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            adds.setCurrentItem(adds.getCurrentItem() <
                                    adds.getAdapter().getCount() - 1 ? adds.getCurrentItem() + 1 : 0);
                        }
                    });

                }
            }
        }, 3000, 3000);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        if (null != addTimer) {
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
                filterPopWindow = new MallFilterDialog(this.getActivity());
                filterPopWindow.initView();
                filterPopWindow.setTarText((TextView) contentView.findViewById(R.id.mall_filter));
                filterPopWindow.setFilterListener(this);
            }
            if (!filterPopWindow.isShowing()) {

                filterPopWindow.showAtRight(5, (int)(((View)filter.getParent()).getY()+v.getY()+v.getHeight()));
            } else {
                filterPopWindow.dismiss();
            }
        } else if (R.id.mall_city_choose == v.getId()) {
            if (null != cityChooser && !cityChooser.isShowing()) {
                cityChooser.showAt(DisplayUtil.dip2px(this.getActivity(), 68),(int)v.getY()+v.getHeight()+DisplayUtil.dip2px(this.getActivity(), 15));
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
            hideLoading();
            fetchProducts(0);
            return;
        }
    }


    private void fetchProducts(int page) {
        mallCallbacks.fetchProducts(page, PER_PAGE, CITY_ID,
                mtBar.getSelectedItem(), mtCIBar.getSelectedItem(),
                null == filterPopWindow ? "" : filterPopWindow.getLocalDest(),
                null == filterPopWindow ? "time" : filterPopWindow.getSortType());
    }

    @Override
    public void updateTabBar() {

    }

    @Override
    public void refreshProductList(int page, List<Product> products) {
        hideLoading();
        currentPage = page;
        if (0 == page) {
            productAdapter.setdata(products);
        } else {
            productAdapter.apenddata(products);
        }


    }

    private void hideLoading() {
        if (null != refreshLayout && refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(false);
        }
        if (null != refreshListView && refreshListView.isRefreshing()) {
            refreshListView.onRefreshComplete();
        }
    }

    @Override
    public void refreshStateChange(String id) {
        if (null != productAdapter) {
            productAdapter.upShelf(id);
        }
    }

    @Override
    public void choosedFilter(String local_id, String sort) {
        fetchProducts(0);
    }

    @Override
    public void onRefresh() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fetchProducts(0);
            }
        }, 1000);

    }

    @Override
    public void choosedCity(String city_name, String city_id) {
        ((TextView) contentView.findViewById(R.id.mall_city_choose)).setText(city_name);
        CITY_ID = city_id;
        if(null != filterPopWindow){
            filterPopWindow.resetIndex();
        }
        fetchProducts(0);
    }
}
