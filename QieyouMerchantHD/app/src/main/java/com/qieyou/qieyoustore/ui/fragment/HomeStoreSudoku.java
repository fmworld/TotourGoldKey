package com.qieyou.qieyoustore.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.fm.fmlib.TourApplication;
import com.fm.fmlib.controllers.MainController;
import com.fm.fmlib.controllers.ProductController;
import com.fm.fmlib.dao.ProductBreviary;
import com.qieyou.qieyoustore.Adapter.StoreSudokuGridAdapter;
import com.qieyou.qieyoustore.BaseTourActivity;
import com.qieyou.qieyoustore.MerchanthdApplication;
import com.qieyou.qieyoustore.R;
import com.qieyou.qieyoustore.ui.widget.AbstLinearIndicator;
import com.qieyou.qieyoustore.ui.widget.AnimListenFragment;
import com.qieyou.qieyoustore.ui.widget.StoreTabBar;

import java.util.List;

/**
 * Created by zhoufeng'an on 2015/8/9.
 */
public class HomeStoreSudoku extends HomeStoreFragment implements View.OnClickListener, AbstLinearIndicator.LinearIndicatorListener,AdapterView.OnItemClickListener {
    private View content;
    private GridView store_sudoku_grid;
    private StoreSudokuGridAdapter gridAdapter;

    public HomeStoreSudoku() {
    }

    public static HomeStoreSudoku create() {
        HomeStoreSudoku fragment = new HomeStoreSudoku();

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        content = inflater.inflate(R.layout.fragment_home_store_sudoku, null);
        ((StoreTabBar) content.findViewById(R.id.store_sdk_tab_bar)).setTarBarItemClickListener(this);
        store_sudoku_grid = (GridView) content.findViewById(R.id.store_sudoku_grid);
        content.findViewById(R.id.store_sudoku_back).setOnClickListener(this);
        gridAdapter = new StoreSudokuGridAdapter(this.getActivity());
        store_sudoku_grid.setAdapter(gridAdapter);
        store_sudoku_grid.setOnItemClickListener(this);
        this.setAnimationListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator animation) {
                if (null != mProductStoreCallbacks) {
                    ((StoreTabBar) content.findViewById(R.id.store_sdk_tab_bar))
                            .setData(TourApplication.instance().getDaoProductTag().getProductTags());
                }
            }
        });
        return content;
    }

    @Override
    public void onResume() {
        super.onResume();
        getController().attachUi(this);

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
        if (R.id.store_sudoku_back == v.getId()) {
            ((BaseTourActivity) this.getActivity()).getDisplay().showHomeMenuItem(MainController.HomeMenu.STORE_GALLERY);
        }
    }


    @Override
    public void onTarBarItemClicked(View view,Object item_seq) {
        gridAdapter.setDataSeq((String)item_seq);
        mProductStoreCallbacks.fetchProBre((String)item_seq);
    }

    @Override
    public void focusChanged(Object data) {

    }

    public void showProductBre(List<ProductBreviary> pros) {
        gridAdapter.setdata(pros);
    }

    public void updateTabBar() {
        if (0 == ((StoreTabBar) content.findViewById(R.id.store_sdk_tab_bar)).getItemCount()){
            ((StoreTabBar) content.findViewById(R.id.store_sdk_tab_bar)).setData(TourApplication.instance().getDaoProductTag().getProductTags());
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putString("item",((ProductBreviary) parent.getAdapter().getItem(position)).getProduct_id());
        ((BaseTourActivity) (this.getActivity()))
                .getDisplay()
                .showHomeSecondContent(MainController.HomeMenu.PRO_DETAIL, bundle);
    }
}
