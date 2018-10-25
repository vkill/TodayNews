package com.kx.todaynews.adapter.provider;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.NewsListAdapter;
import com.kx.todaynews.bean.BackGround;
import com.kx.todaynews.bean.HotContent;

/**
 * Created by admin on 2018/10/25.
 */
public class LiveItemProvider  extends BaseNewsItemProvider {

    @Override
    public int viewType() {
        return NewsListAdapter.TYPE_LIVE;
    }

    @Override
    public int layout() {
        return R.layout.item_hot_data_live_broadcast;
    }
    @Override
    void setData(BaseViewHolder helper, HotContent hotContent,int position) {
        BackGround background = hotContent.getBackground();
        if (background!=null){
            BackGround.VideoBean video = background.getVideo();
            if (video!=null){
                ImageView iv_1 = helper.getView(R.id.iv_1);
                Glide.with(mContext).load(video.getCovers()).into(iv_1);
            }
        }
        helper.setText(R.id.status_display,hotContent.getStatus_display());
        helper.setText(R.id.participated_count,String.format("%s%S",hotContent.getParticipated(),hotContent.getParticipated_suffix()));
    }
}
