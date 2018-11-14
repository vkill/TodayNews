package com.kx.todaynews.module.map;

import android.support.v4.app.FragmentTransaction;

import com.kx.todaynews.R;
import com.kx.todaynews.base.BaseActivity;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.module.map.fragment.SearchPoiFragment;

public class RouteShowActivity extends BaseActivity {

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_route_show;
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {
        SearchPoiFragment searchPoiFragment = new SearchPoiFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_container,searchPoiFragment);
        fragmentTransaction.commit();
    }
}
