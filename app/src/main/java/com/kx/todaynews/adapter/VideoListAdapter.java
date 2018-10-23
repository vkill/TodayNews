package com.kx.todaynews.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.utils.UiUtils;
import com.kx.todaynews.widget.helper.MyJZVideoPlayerStandard;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * @description: 视频列表的Adapter
 */

public class VideoListAdapter extends BaseQuickAdapter<HotContent,BaseViewHolder> {

    public VideoListAdapter(int layoutResId, @Nullable List<HotContent> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, HotContent news) {
        if (TextUtils.isEmpty(news.getTitle())){
            //如果没有标题，则直接跳过
            return;
        }

        helper.setVisible(R.id.ll_title,true);//显示标题栏
        helper.setText(R.id.tv_title, news.getTitle());//设置标题
//
        String format = UiUtils.getString(R.string.video_play_count);
        int watchCount = news.getVideo_detail_info().getVideo_watch_count();
        String countUnit = "";
        if (watchCount> 10000){
            watchCount = watchCount / 10000;
            countUnit = "万";
        }
//
        helper.setText(R.id.tv_watch_count, String.format(format, watchCount + countUnit));//播放次数
        Glide.with(mContext).load(news.getUser_info().getAvatar_url())
                .transform(new GlideCircleTransform(mContext)).into((ImageView) helper.getView(R.id.iv_avatar));
        helper.setVisible(R.id.ll_duration, true)//显示时长
                .setText(R.id.tv_duration, TyDateUtils.getTimeStrBySecond(news.getVideo_duration()));//设置时长

        helper.setText(R.id.tv_author, news.getUser_info().getName())//昵称
                .setText(R.id.tv_comment_count, String.valueOf(news.getComment_count()));//评论数

//
        MyJZVideoPlayerStandard videoPlayer = helper.getView(R.id.video_player);
        Glide.with(mContext).load(news.getVideo_detail_info().getDetail_video_large_image().getUrl())
               .into(videoPlayer.thumbImageView);

//        videoPlayer.setAllControlsVisible(View.GONE, View.GONE, View.VISIBLE, View.GONE, View.VISIBLE, View.VISIBLE, View.GONE);
//        videoPlayer.tinyBackImageView.setVisibility(View.GONE);
//        videoPlayer.setPosition(helper.getAdapterPosition());//绑定Position

        videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
//        videoPlayer.setOnVideoClickListener(new OnVideoClickListener() {
//            @Override
//            public void onVideoClickToStart() {
//                //点击播放
//                helper.setVisible(R.id.ll_duration, false);//隐藏时长
//                helper.setVisible(R.id.ll_title,false);//隐藏标题栏
//                //
//                VideoPathDecoder decoder = new VideoPathDecoder() {
//                    @Override
//                    public void onSuccess(String url) {
//                        KLog.i("Video url:" + url);
//                        UIUtils.postTaskSafely(new Runnable() {
//                            @Override
//                            public void run() {
//                                videoPlayer.setUp(url, JCVideoPlayer.SCREEN_LAYOUT_LIST,news.title);
//                                videoPlayer.seekToInAdvance = news.video_detail_info.progress;
//                                videoPlayer.startVideo();
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onDecodeError() {
//                    }
//                };
//                decoder.decodePath(news.url);
//            }
//        });
    }
}
