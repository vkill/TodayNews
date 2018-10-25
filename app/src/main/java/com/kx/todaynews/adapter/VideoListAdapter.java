package com.kx.todaynews.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.Api;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.module.video.VideoContentBean;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.utils.UiUtils;

import java.util.List;
import java.util.Random;
import java.util.zip.CRC32;

import cn.jzvd.JzvdStd;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


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

        JzvdStd videoPlayer = helper.getView(R.id.video_player);
        //Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
       // Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        Glide.with(mContext).load(news.getVideo_detail_info().getDetail_video_large_image().getUrl())
               .into(videoPlayer.thumbImageView);
        videoPlayer.titleTextView.setText("");//清除标题,防止复用的时候出现
        NetClient.getInstance().get(Api.class)
                .getVideoInfo(getVideoContentApi(news.getVideo_id()))
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<VideoContentBean>() {
                    private String mMediaSmooth;
                    private String mMediaMedium;
                    private String mMediaHigh;
                    private String mMediaSuper;
                    @Override
                    public void accept(VideoContentBean videoInfo) throws Exception {
                        VideoContentBean.DataBean.VideoListBean videoList = videoInfo.getData().getVideo_list();
                        if (videoList.getVideo_1() != null) {
                            String base64 = videoList.getVideo_1().getMain_url();
                            mMediaSmooth = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            videoPlayer.setUp(mMediaSmooth, "",JzvdStd.SCREEN_WINDOW_NORMAL);
                        }
                        if (videoList.getVideo_2() != null) {
                            String base64 = videoList.getVideo_2().getMain_url();
                            mMediaMedium = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                        }
                        if (videoList.getVideo_3() != null) {
                            String base64 = videoList.getVideo_3().getMain_url();
                            mMediaHigh = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                        }
                        if (videoList.getVideo_4() != null) {
                            String base64 = videoList.getVideo_4().getMain_url();
                            mMediaSuper = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.toString());
                    }
                });
    }
    private static String getVideoContentApi(String videoid) {
        String VIDEO_HOST = "http://lf.snssdk.com";
        String VIDEO_URL = "/video/urls/v/1/toutiao/mp4/%s?r=%s";
        String r = getRandom();
        String s = String.format(VIDEO_URL, videoid, r);
        // 将/video/urls/v/1/toutiao/mp4/{videoid}?r={Math.random()} 进行crc32加密
        CRC32 crc32 = new CRC32();
        crc32.update(s.getBytes());
        String crcString = crc32.getValue() + "";
        String url = VIDEO_HOST + s + "&s=" + crcString;
        return url;
    }

    private static String getRandom() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
