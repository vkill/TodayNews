package com.kx.todaynews.adapter.provider;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.NewsListAdapter;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.bean.MiddleImage;

/**
 * Created by admin on 2018/10/25.
 */
public class OnePicItemProvider extends BaseNewsItemProvider {

    @Override
    public int viewType() {
        return NewsListAdapter.TYPE_ONE_IMAGE;
    }

    @Override
    public int layout() {
        return R.layout.item_hot_data_one_image;
    }
    @Override
    void setData(BaseViewHolder helper, HotContent hotContent,int position) {
        MiddleImage middle_image = hotContent.getMiddle_image();
        if (middle_image!=null){
            ImageView iv1 =  helper.getView(R.id.iv_1);
            Glide.with(mContext).load(middle_image.getUrl()).into(iv1);
        }
        int gallary_image_count = hotContent.getGallary_image_count();
        if (gallary_image_count >1){
            helper.setVisible(R.id.tv_image_count,true);
            helper.setText(R.id.tv_image_count,String.format("%s图",gallary_image_count));
        }

    }
}
