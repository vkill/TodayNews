package com.kx.todaynews.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.bean.ImageList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/11/5.
 */
public class ImageListAdapter extends BaseQuickAdapter<HotContent,BaseViewHolder> {

    public ImageListAdapter(int layoutResId, @Nullable List<HotContent> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, HotContent hotContent) {
        int gallary_image_count = hotContent.getGallary_image_count();
        helper.setText(R.id.tv_image_count,String.format("%s图",gallary_image_count));
        helper.setText(R.id.title,String.format("%s",hotContent.getTitle()));
        helper.setText(R.id.media_name,String.format("%s",hotContent.getSource()));
        helper.setText(R.id.comment_count,String.format("%s评论",hotContent.getComment_count()));
        ArrayList<ImageList> image_list = hotContent.getImage_list();
        ImageView iv1 =  helper.getView(R.id.iv_bg);
        if (image_list !=null && image_list .size()>0 ){
            Glide.with(mContext).load(image_list.get(0).getUrl()).into(iv1);
        }else if ( hotContent.getMiddle_image() !=null){
            Glide.with(mContext).load(hotContent.getMiddle_image().getUrl()).into(iv1);
        }
    }
}
