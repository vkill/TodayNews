package com.kx.todaynews.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;

import com.kx.todaynews.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * MVP模式的Base Activity
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        mPresenter = createPresenter();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        initToolbar();
        initEventAndData();
    }
//    @Override
//    protected void onViewCreated() {
//        if (mPresenter != null) {
//            mPresenter.attachView(this);
//        }
//    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }


    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }


    @Override
    public void showToast(String message) {
        ToastUtils.showToast(message,this);
    }
    @Override
    public void showErrorMsg(String errorMsg) {
        ToastUtils.showToast(errorMsg,this);
    }

    /**
     * 创建Presenter
     * @return P Presenter
     */
    protected abstract P createPresenter();

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化ToolBar
     */
    protected abstract void initToolbar();

    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();
}
