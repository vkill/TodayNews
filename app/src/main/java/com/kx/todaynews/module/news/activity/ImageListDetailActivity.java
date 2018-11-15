package com.kx.todaynews.module.news.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.widget.TextView;

import com.kx.todaynews.R;
import com.kx.todaynews.adapter.ImageListDetailAdapter;
import com.kx.todaynews.base.BaseActivity;
import com.kx.todaynews.bean.ImageListDetailBean;
import com.kx.todaynews.contract.IImageListDetailContract;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.presenter.ImageListDetailPresenter;
import com.kx.todaynews.widget.DragLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class ImageListDetailActivity extends BaseActivity<ImageListDetailPresenter> implements IImageListDetailContract.IImageListDetailView {

    public static final String GROUPID = "GROUPID";

    @BindView(R.id.imageListViewPager)
    ViewPager imageListViewPager;
    @BindView(R.id.tv_gallery)
    TextView tvGallery;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;
    boolean isShow = true;
    @BindView(R.id.dragLayout)
    DragLayout dragLayout;
    private ImageListDetailAdapter mImageListDetailAdapter;
    private List<ImageListDetailBean.DataBean.GalleryBean> mGallery;
    private ObjectAnimator mOutAnimator;
    private ObjectAnimator mInAnimator;

    @Override
    protected ImageListDetailPresenter createPresenter() {
        return new ImageListDetailPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_list_detail;
    }

    @Override
    protected void initListener() {
        imageListViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                ImageListDetailBean.DataBean.GalleryBean galleryBean = mGallery.get(position);
                SpannableString spannableString = new SpannableString((position + 1) + "/" + mGallery.size() +" "+ galleryBean.getSub_abstract());
                RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
                int start = 1;
                int end = 3;
                if (mGallery.size() < 10) {
                    end = 3;
                } else if ( position <= 8 ){
                    end = 4;
                }else  {
                    start = 2;
                    end = 5;
                }
                spannableString.setSpan(sizeSpan, start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                tvGallery.setText(spannableString);
            }
        });
        mImageListDetailAdapter.setOnPhotoClickListener(new ImageListDetailAdapter.OnPhotoClickListener() {
            @Override
            public void onPhotoClick() {
                if (isShow){
                    dragLayout.scrollOutScreen(500);
                    scrollOutScreen(500);
                }else {
                    dragLayout.scrollInScreen(500);
                    scrollInScreen(500);
                }
                isShow = !isShow ;
            }
        });
        //  上 / 下  滑动监听   关闭Activity
        dragLayout.setOnSlideExitScrollListener(new DragLayout.onSlideExitScrollListener() {
            @Override
            public void onSlideExit() {
                finish();
            }
        });
    }

    //  alpha
    private void scrollOutScreen(int duration) {
        if (mOutAnimator==null){
            mOutAnimator = ObjectAnimator.ofFloat(toolbar, "translationY", 0, - toolbar.getHeight());
        }
        mOutAnimator.setDuration(duration);
        mOutAnimator.start();
    }

    private void scrollInScreen(int duration) {
        if (mInAnimator==null){
            mInAnimator = ObjectAnimator.ofFloat(toolbar, "translationY",  - toolbar.getHeight(),0);
        }
        mInAnimator.setDuration(duration);
        mInAnimator.start();
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // toolbar.setNavigationIcon(R.drawable.);
    }

    @Override
    protected void initEventAndData() {
       // getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mImageListDetailAdapter = new ImageListDetailAdapter(this);
        imageListViewPager.setAdapter(mImageListDetailAdapter);
        RetrofitUrlManager.getInstance().putDomain("a3", NetClient.HOST_A3);
        String groupId = getIntent().getStringExtra(GROUPID);
        mPresenter.getImageListDetail(groupId);

    }


    @Override
    public void showImageListDetail(ImageListDetailBean imageListDetailBean) {
        ImageListDetailBean.DataBean data = imageListDetailBean.getData();
        List<ImageListDetailBean.DataBean.ImageDetailBean> image_detail = data.getImage_detail();
        mImageListDetailAdapter.setImageDetail(image_detail);

        mGallery = data.getGallery();
        SpannableString spannableString = new SpannableString("1/" + mGallery.size() + " "+mGallery.get(0).getSub_abstract());
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
        int end = 3;
        if (mGallery.size() >= 10) {
            end = 4;
        }
        spannableString.setSpan(sizeSpan, 1, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvGallery.setText(spannableString);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
