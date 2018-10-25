package com.kx.todaynews.adapter.provider;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.NewsListAdapter;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.bean.LargeImageList;
import com.kx.todaynews.bean.VideoDetailInfo;
import com.kx.todaynews.utils.TyDateUtils;

import java.util.ArrayList;

/**
 * Created by admin on 2018/10/25.
 */
public class VideoItemProvider extends BaseNewsItemProvider {

    @Override
    public int viewType() {
        return NewsListAdapter.TYPE_VIDEO;
    }

    @Override
    public int layout() {
        return R.layout.item_hot_data_video;
    }
    @Override
    void setData(BaseViewHolder helper, HotContent hotContent,int position) {
        //  视频时长  单位 秒   需要自行转换成  HH：mm : ss  格式
        int video_duration = hotContent.getVideo_duration();
        helper.setText(R.id.tv_video_duration,String.format("%s", TyDateUtils.getTimeStrBySecond(video_duration)));
        VideoDetailInfo video_detail_info = hotContent.getVideo_detail_info();
        ImageView iv_1 = helper.getView(R.id.iv_1);
        if (video_detail_info!=null){
            Glide.with(mContext).load(video_detail_info.getDetail_video_large_image().getUrl()).into(iv_1);
        }else {
            ArrayList<LargeImageList> large_image_list = hotContent.getLarge_image_list();
            if (large_image_list!=null&& large_image_list.size()>0){
                Glide.with(mContext).load(large_image_list.get(0).getUrl()).into(iv_1);
            }
        }
    }
}
