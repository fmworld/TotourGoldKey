package com.qieyou.qieyoustore.ui.fragment;

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
import com.fm.fmlib.dao.ProductBreviary;
import com.qieyou.qieyoustore.Adapter.StoreViewPagerAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.StoreTabBar;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeStoreViewPager extends HomeStoreFragment implements View.OnClickListener,StoreTabBar.TarBarItemClickListener {
    private View content;
    private StoreViewPagerAdapter vpAdapter;
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
        ((StoreTabBar)content.findViewById(R.id.store_gallery_tab_bar)).setTarBarItemClickListener(this);
        vpAdapter = new StoreViewPagerAdapter(this.getActivity());
        ((ViewPager)content.findViewById(R.id.store_view_pager)).setAdapter(vpAdapter);
        ((ViewPager)content.findViewById(R.id.store_view_pager)).setPageTransformer(true, new DepthPageTransformer());
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);
        ((StoreTabBar)content.findViewById(R.id.store_gallery_tab_bar)).setData(TourApplication.instance().getDaoProductTag().getProductTags());
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
        if(R.id.store_sudoku_choose_button ==  v.getId()){
            ((BaseTourActivity)getActivity()).getDisplay().showHomeSecondContent(MainController.HomeMenu.STORE_SUDOKU);
        }
    }

    @Override
    public void onTarBarItemClicked(String item_seq) {
        vpAdapter.setDataSeq(item_seq);
        mProductStoreCallbacks.fetchProBre(item_seq);
    }

    public void showProductBre(List<ProductBreviary> pros) {
        Log.v("storegallery","showProductBre");
        vpAdapter.setdata(pros);
    }

    public void updateTabBar() {
        if (0 == ((StoreTabBar) content.findViewById(R.id.store_gallery_tab_bar)).getItemCount()){
            ((StoreTabBar) content.findViewById(R.id.store_gallery_tab_bar)).setData(TourApplication.instance().getDaoProductTag().getProductTags());
        }
    }
}
