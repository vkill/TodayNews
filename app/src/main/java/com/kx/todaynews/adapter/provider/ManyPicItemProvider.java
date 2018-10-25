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
public class ManyPicItemProvider extends BaseNewsItemProvider {

    @Override
    public int viewType() {
        return NewsListAdapter.TYPE_MANY_IMAGE;
    }

    @Override
    public int layout() {
        return R.layout.item_hot_data_many_image;
    }
    @Override
    void setData(BaseViewHolder helper, HotContent hotContent,int position) {
        int gallary_image_count = hotContent.getGallary_image_count();
        helper.setText(R.id.tv_image_count,String.format("%s图",gallary_image_count));
        ArrayList<ImageList> image_list = hotContent.getImage_list();
        ImageView iv1 =  helper.getView(R.id.iv_1);
        if (image_list !=null && image_list .size()>0 ){
            Glide.with(mContext).load(image_list.get(0).getUrl()).into(iv1);
        }else if ( hotContent.getMiddle_image() !=null){
            Glide.with(mContext).load(hotContent.getMiddle_image().getUrl()).into(iv1);
        }
    }
}
