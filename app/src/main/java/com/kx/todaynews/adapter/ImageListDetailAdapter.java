package com.kx.todaynews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.kx.todaynews.bean.ImageListDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/11/6.
 *  组图界面的图片适配器
 */
public class ImageListDetailAdapter  extends PagerAdapter {

    List<ImageListDetailBean.DataBean.ImageDetailBean> imageDetail = new ArrayList<>();

    private Context mContext;

    public ImageListDetailAdapter(Context context) {
        mContext = context;
    }

    public void setImageDetail(List<ImageListDetailBean.DataBean.ImageDetailBean> imageDetail) {
        this.imageDetail = imageDetail;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return imageDetail.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        PhotoView photoView = new PhotoView(mContext);
        Glide.with(mContext).load(imageDetail.get(position).getUrl()).into(photoView);
        container.addView(photoView);

        return photoView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
