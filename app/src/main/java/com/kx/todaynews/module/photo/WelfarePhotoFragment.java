package com.kx.todaynews.module.photo;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kx.todaynews.R;
import com.kx.todaynews.base.BaseFragment;
import com.kx.todaynews.bean.PhotoListBean;
import com.kx.todaynews.contract.IWelfarePhotoContract;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.widget.fab.FloatingActionButton;
import com.kx.todaynews.widget.fab.FloatingActionsMenu;
import com.kx.todaynews.widget.fab.OnFloatingActionButtonClickListener;

import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class WelfarePhotoFragment extends BaseFragment<WelfarePhotoPresenter> implements IWelfarePhotoContract.IPhotoView{

    @BindView(R.id.photoRecyclerView)
    RecyclerView photoRecyclerView;
    @BindView(R.id.floatingActionsMenu)
    FloatingActionsMenu floatingActionsMenu;
    private WelfarePhotoAdapter mWelfarePhotoAdapter;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;

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
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        photoRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
        mWelfarePhotoAdapter = new WelfarePhotoAdapter(getPhotoWidth(true));
        photoRecyclerView.setAdapter(mWelfarePhotoAdapter);
    }

    @Override
    protected void initListener() {
        mWelfarePhotoAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMorePhotoData();
            }
        },photoRecyclerView);
        floatingActionsMenu.setOnFloatingActionButtonClickListener(new OnFloatingActionButtonClickListener() {
            @Override
            public void OnFloatingActionButtonClick(FloatingActionButton view, int position) {
               if (position == 0){
                   mWelfarePhotoAdapter.setPhotoWidth(getPhotoWidth(true));
                   photoRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);
               }else if (position ==1){
                    if (mLinearLayoutManager ==null){
                        mLinearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
                    }
                   mWelfarePhotoAdapter.setPhotoWidth(getPhotoWidth(false));
                   photoRecyclerView.setLayoutManager(mLinearLayoutManager);
               }
            }
        });
    }

    /**
     *  StaggeredGridLayoutManager 瀑布流 isHalfScreen = true
     *  LinearLayoutManager     线性布局  isHalfScreen = false
     * @param isHalfScreen 图片宽度是否是半个屏幕宽度
     * @return  photoWidth
     */
    private int getPhotoWidth(boolean isHalfScreen){
        int widthPixels = mActivity.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = mActivity.getResources().getDimensionPixelOffset(R.dimen.photo_margin_width);
        if (isHalfScreen){
            return widthPixels / 2 - marginPixels;
        }else {
            return widthPixels  - marginPixels;
        }
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
        mWelfarePhotoAdapter.loadMoreComplete();
    }

    @Override
    public void showError() {
        showToast("请求出错啦");
        mWelfarePhotoAdapter.loadMoreFail();
    }
}
