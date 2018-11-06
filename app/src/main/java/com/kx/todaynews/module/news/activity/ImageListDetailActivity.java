package com.kx.todaynews.module.news.activity;

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

import java.util.List;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class ImageListDetailActivity extends BaseActivity<ImageListDetailPresenter> implements IImageListDetailContract.IImageListDetailView {

    public static final String GROUPID = "GROUPID";

    @BindView(R.id.imageListViewPager)
    ViewPager imageListViewPager;
    @BindView(R.id.tv_gallery)
    TextView tvGallery;
    @BindView(R.id.tool_bar)
    Toolbar toolbar;

    private ImageListDetailAdapter mImageListDetailAdapter;
    private List<ImageListDetailBean.DataBean.GalleryBean> mGallery;

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
        imageListViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ImageListDetailBean.DataBean.GalleryBean galleryBean = mGallery.get(position);


                SpannableString spannableString = new SpannableString((position+1)+"/"+mGallery.size()+galleryBean.getSub_abstract());
                RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
                int end = 3;
                if (mGallery.size()>=10){
                    end = 4 ;
                }
                spannableString.setSpan(sizeSpan,1,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                tvGallery.setText(spannableString);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // toolbar.setNavigationIcon(R.drawable.);
    }

    @Override
    protected void initEventAndData() {
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
        SpannableString spannableString = new SpannableString("1/"+mGallery.size()+mGallery.get(0).getSub_abstract());
        RelativeSizeSpan sizeSpan = new RelativeSizeSpan(0.7f);
        int end = 3;
        if (mGallery.size()>=10){
            end = 4 ;
        }
        spannableString.setSpan(sizeSpan,1,end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
}
