package com.kx.todaynews.module.photo;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.dl7.player.utils.StringUtils;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.PhotoListBean;
import com.orhanobut.logger.Logger;

public class WelfarePhotoAdapter extends BaseQuickAdapter<PhotoListBean.ResultsBean,BaseViewHolder> {

    // 图片的宽度
    private int mPhotoWidth;

    public WelfarePhotoAdapter(Context context) {
        super(R.layout.adapter_welfare_photo);
        int widthPixels = context.getResources().getDisplayMetrics().widthPixels;
        int marginPixels = context.getResources().getDimensionPixelOffset(R.dimen.photo_margin_width);
        mPhotoWidth = widthPixels / 2 - marginPixels;
    }
    @Override
    protected void convert(BaseViewHolder holder, PhotoListBean.ResultsBean item) {
        final ImageView ivPhoto = holder.getView(R.id.iv_photo);
      //  int photoHeight = calcPhotoHeight(item.getPixel(), mPhotoWidth);
        // 返回的数据有像素分辨率，根据这个来缩放图片大小
     //   final ViewGroup.LayoutParams params = ivPhoto.getLayoutParams();
    //    params.width = mPhotoWidth;
    //    params.height = photoHeight;
    //    ivPhoto.setLayoutParams(params);
        Glide.with(mContext).load(item.getUrl()).fitCenter().dontAnimate().into(ivPhoto);
        holder.setText(R.id.tv_title, item.getCreatedAt());
    }
    /**
     * 计算图片要显示的高度
     * @param pixel 原始分辨率
     * @param width 要显示的宽度
     * @return
     */
    public static int calcPhotoHeight(String pixel, int width) {
        int height = -1;
        int index = pixel.indexOf("*");
        if (index != -1) {
            try {
                int widthPixel = Integer.parseInt(pixel.substring(0, index));
                int heightPixel = Integer.parseInt(pixel.substring(index + 1));
                height = (int) (heightPixel * (width * 1.0f / widthPixel));
            } catch (NumberFormatException e) {
                Logger.e(e.toString());
                return -1;
            }
        }

        return height;
    }
}
