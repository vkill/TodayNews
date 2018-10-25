package com.kx.todaynews.adapter.provider;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.NewsListAdapter;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.bean.ImageList;

import java.util.ArrayList;

/**
 * Created by admin on 2018/10/25.
 */
public class ThreePicItemProvider extends BaseNewsItemProvider {

    @Override
    public int viewType() {
        return NewsListAdapter.TYPE_THREE_IMAGE;
    }

    @Override
    public int layout() {
        return R.layout.item_hot_data_three_image;
    }
    @Override
    void setData(BaseViewHolder helper, HotContent hotContent,int position) {
        ImageView iv_1 = helper.getView(R.id.iv_1);
        ImageView iv_2 = helper.getView(R.id.iv_2);
        ImageView iv_3 = helper.getView(R.id.iv_3);
        ArrayList<ImageList> image_list = hotContent.getImage_list();
        Glide.with(mContext).load(image_list.get(0).getUrl()).into(iv_1);
        Glide.with(mContext).load(image_list.get(1).getUrl()).into(iv_2);
        Glide.with(mContext).load(image_list.get(2).getUrl()).into(iv_3);
    }
}
