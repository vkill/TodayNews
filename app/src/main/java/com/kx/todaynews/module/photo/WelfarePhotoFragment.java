package com.kx.todaynews.module.photo;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.kx.todaynews.R;
import com.kx.todaynews.base.BaseFragment;
import com.kx.todaynews.bean.PhotoListBean;
import com.kx.todaynews.contract.IWelfarePhotoContract;
import com.kx.todaynews.net.NetClient;

import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class WelfarePhotoFragment extends BaseFragment<WelfarePhotoPresenter> implements IWelfarePhotoContract.IPhotoView{

    @BindView(R.id.photoRecyclerView)
    RecyclerView photoRecyclerView;
    private WelfarePhotoAdapter mWelfarePhotoAdapter;

    public static WelfarePhotoFragment getInstance() {
        return new WelfarePhotoFragment();
    }

    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected WelfarePhotoPresenter createPresenter() {
        return new WelfarePhotoPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_photo;
    }

    @Override
    protected void initEventAndData() {
        RetrofitUrlManager.getInstance().putDomain("gank", NetClient.HOST_GANK);
        photoRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mWelfarePhotoAdapter = new WelfarePhotoAdapter(mActivity);
        photoRecyclerView.setAdapter(mWelfarePhotoAdapter);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void loadData() {
        mPresenter.getPhotoData();
    }


    @Override
    public void showPhotoData(List<PhotoListBean.ResultsBean> beans) {
        mWelfarePhotoAdapter.replaceData(beans);
    }

    @Override
    public void showMorePhotoData(List<PhotoListBean.ResultsBean> beans) {
        mWelfarePhotoAdapter.addData(beans);
    }

    @Override
    public void showError() {
        super.showError();
    }
}
