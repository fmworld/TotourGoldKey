package com.totour.qieyoumerchanthd;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fm.fmlib.Display;
import com.fm.fmlib.controllers.MainController;
import com.totour.qieyoumerchanthd.Adapter.HomeNavigationAdapter;

/**
 * Created by zhoufeng'an on 2015/8/5.
 */
public class HomeAcitvity extends BaseTourActivity implements View.OnClickListener {
    private MainController.HomeMenu currentMenuTag;
    private HomeNavigationAdapter naviAdapter;

    protected int getContentViewLayoutId() {
        return R.layout.activity_home;
    }

    protected void handleIntent(Intent intent, Display display) {
        initNavigation();
    }

    private void initNavigation() {
        Log.v(TAG, "initNavigation ");
        naviAdapter = new HomeNavigationAdapter(this);
        ((ListView) findViewById(R.id.home_items)).setAdapter(naviAdapter);
        ((ListView) findViewById(R.id.home_items)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "setOnItemClickListener ");
                naviAdapter.onItemClick(position);
                HomeAcitvity.this.getDisplay().showHomeMenuItem((MainController.HomeMenu) naviAdapter.getItem(position));
            }
        });
        findViewById(R.id.home_navi_user).setOnClickListener(this);
//        ((SimpleDraweeView)findViewById(R.id.home_navi_user)).setImageURI(Uri.parse("http://img1.fjtv.net/material/news/img/2015/07/4b9db2f97de68cbf05df2517be05db1a.jpg"));
    }

    public MainController.HomeMenu getCurrentMenuTag() {
        return currentMenuTag;
    }

    public void setCurrentMenuTag(MainController.HomeMenu currentMenuTag) {
        this.currentMenuTag = currentMenuTag;
    }

    @Override
    public void onClick(View v) {
        if (R.id.home_navi_user == v.getId()) {
            this.getDisplay().showHomeProfileItem(MainController.HomeMenu.PERSON_INFO);
        }
    }
}
