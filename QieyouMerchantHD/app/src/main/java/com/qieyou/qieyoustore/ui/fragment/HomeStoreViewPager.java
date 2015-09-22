package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.viewpager.transforms.CubeInTransformer;
import com.ToxicBakery.viewpager.transforms.DepthPageTransformer;
import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.dao.LaunchProfile;
import com.fm.fmlib.dao.ProductBreviary;
import com.fm.fmlib.dao.ProductTag;
import com.fm.fmlib.state.ProductState;
import com.qieyou.qieyoustore.Adapter.StoreViewPagerAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.HomeAcitvity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AbstLinearIndicator;
import com.qieyou.qieyoustore.ui.widget.StoreNumTextIndicator;
import com.qieyou.qieyoustore.ui.widget.StoreTabBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeStoreViewPager extends HomeStoreFragment implements View.OnClickListener, AbstLinearIndicator.LinearIndicatorListener, ViewPager.OnPageChangeListener {
    private View content;
    private StoreViewPagerAdapter vpAdapter;
    private StoreNumTextIndicator storeVPIndicator;
    private StoreTabBar mStoreTabBar;

    public HomeStoreViewPager() {
    }

    public static HomeStoreViewPager create() {
        HomeStoreViewPager fragment = new HomeStoreViewPager();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_home_store, null);
        content.findViewById(R.id.store_sudoku_choose_button).setOnClickListener(this);

        mStoreTabBar = ((StoreTabBar) content.findViewById(R.id.store_gallery_tab_bar));
        mStoreTabBar.setTarBarItemClickListener(this);

        vpAdapter = new StoreViewPagerAdapter(this.getActivity());
        ((ViewPager) content.findViewById(R.id.store_view_pager)).setAdapter(vpAdapter);
        ((ViewPager) content.findViewById(R.id.store_view_pager)).setPageTransformer(true, new DepthPageTransformer());
        ((ViewPager) content.findViewById(R.id.store_view_pager)).addOnPageChangeListener(this);
        storeVPIndicator = ((StoreNumTextIndicator) content.findViewById(R.id.store_vp_indicator));
        storeVPIndicator.setLinearIndicatorListener(this);


        this.setAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (null != mProductStoreCallbacks) {
                    initTabBarData();
                }
            }
        });
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        ((HomeAcitvity) getActivity()).selectNavigationItem(MainController.HomeMenu.STORE_GALLERY);
    }

    @Override
    public void onPause() {
        getController().detachUi(this);
        super.onPause();
    }

    ProductController getController() {
        return MerchanthdApplication.instance().getmMainController().getProductController();
    }

    @Override
    public void onClick(View v) {
        if (R.id.store_sudoku_choose_button == v.getId()) {
            ((BaseTourActivity) getActivity()).getDisplay().showHomeMenuItem(MainController.HomeMenu.STORE_SUDOKU);
        }
    }


    public void showProductBre(List<ProductBreviary> pros) {
        content.findViewById(R.id.store_item_empty).setVisibility(View.GONE);
        content.findViewById(R.id.store_view_pager).setVisibility(View.VISIBLE);
        storeVPIndicator.setVisibility(View.VISIBLE);

        vpAdapter.setdata(pros);
        int count = pros.size();
        List<String> items = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            items.add(String.valueOf(i));
        }
        storeVPIndicator.setData(items);


    }

    public void updateTabBar() {
        if (0 == mStoreTabBar.getItemCount()) {
            Log.v("mStoreTabBar", "updateTabBar  ");
            initTabBarData();
            Log.v("mStoreTabBar", "updateTabBar after ");
        }
    }

    private void initTabBarData() {
        List<ProductTag> tags = TourApplication.instance().getDaoProductTag().getProductTags();
        List<LaunchProfile> profiles = TourApplication.instance().getDaoLaunProfile()
                .getLaunProfiles(ProductState.LaunchProfileType.slider.toString());
        if (null != profiles && 0 < profiles.size()) {
            ProductTag ptag = new ProductTag();
            ptag.setTag_name(HomeStoreViewPager.this.getString(R.string.town_traver_title));
            ptag.setItem_seq(profiles.get(0).getLink());
            tags.add(0, ptag);
        }
        mStoreTabBar.setData(tags);
    }

    @Override
    public void onTarBarItemClicked(View view, Object item_seq) {
        if (R.id.store_gallery_tab_bar == view.getId()) {

            String target = (String) item_seq;
            vpAdapter.setDataSeq(target);
            if (!((String) item_seq).contains("http")) {
                Log.v("mStoreTabBar", "contains  " + target);
                mProductStoreCallbacks.fetchProBre((String) item_seq);
            } else {
                Log.v("mStoreTabBar", "!contains  " + target);
                List<String> indexs = new ArrayList<>();
                indexs.add("1");
                storeVPIndicator.setData(indexs);
            }


        } else if (R.id.store_vp_indicator == view.getId()) {
            ((ViewPager) content.findViewById(R.id.store_view_pager)).setCurrentItem(Integer.valueOf((String) item_seq) - 1);
        }

    }

    @Override
    public void focusChanged(Object data) {
        storeVPIndicator.updateFoucusState(data);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (null != storeVPIndicator) {
            storeVPIndicator.selecteItem(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void showEmptyItem() {
        content.findViewById(R.id.store_item_empty).setVisibility(View.VISIBLE);
        content.findViewById(R.id.store_view_pager).setVisibility(View.GONE);
        storeVPIndicator.setVisibility(View.GONE);
    }
}
