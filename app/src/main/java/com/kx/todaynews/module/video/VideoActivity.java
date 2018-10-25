package com.kx.todaynews.module.video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dl7.player.media.IjkPlayerView;
import com.kx.todaynews.Api;
import com.kx.todaynews.R;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.utils.LogUtils;

import java.util.Random;
import java.util.zip.CRC32;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class VideoActivity extends AppCompatActivity {
    public static final String ITEM_ID = "item_id";
    public static final String VIDEO_ID = "video_id";
    public static final String THUMBIMAGEVIEW = "thumbImageView";
    public static final String TITLETEXTVIEW = "titleTextView";
    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.videoplayer)
    JzvdStd videoPlayer;
    @BindView(R.id.video_player)
    IjkPlayerView ijkPlayerView;
    private String mItemId;
    private String mVideoId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);
        mItemId = getIntent().getStringExtra(ITEM_ID);
        mVideoId = getIntent().getStringExtra(VIDEO_ID);
        Glide.with(this).load(getIntent().getStringExtra(THUMBIMAGEVIEW))
                .into(ijkPlayerView.mPlayerThumb);
        Glide.with(this).load(getIntent().getStringExtra(THUMBIMAGEVIEW))
                .into(videoPlayer.thumbImageView);
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        StringBuilder stringBuilder = new StringBuilder();
        NetClient.getInstance().get(Api.class)
                .getVideoInfo(getVideoContentApi(mVideoId))
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
                            videoPlayer.setUp(mMediaSmooth, getIntent().getStringExtra(TITLETEXTVIEW),JzvdStd.SCREEN_WINDOW_NORMAL);
                            stringBuilder.append("url1=" + mMediaSmooth + "\n");
                        }
                        if (videoList.getVideo_2() != null) {
                            String base64 = videoList.getVideo_2().getMain_url();
                            mMediaMedium = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            stringBuilder.append("url2=" + mMediaMedium + "\n");
                        }
                        if (videoList.getVideo_3() != null) {
                            String base64 = videoList.getVideo_3().getMain_url();
                            mMediaHigh = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            stringBuilder.append("url3=" + mMediaHigh + "\n");
                        }
                        if (videoList.getVideo_4() != null) {
                            String base64 = videoList.getVideo_4().getMain_url();
                            mMediaSuper = (new String(Base64.decode(base64.getBytes(), Base64.DEFAULT)));
                            stringBuilder.append("url4=" + mMediaSuper + "\n");
                        }
                        ijkPlayerView.init()
                                .setTitle( getIntent().getStringExtra(TITLETEXTVIEW))
                                .setVideoSource(mMediaSmooth, mMediaMedium, mMediaHigh, mMediaSuper, null);
                        tvUrl.setText(stringBuilder.toString());
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

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        if (ijkPlayerView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() { //选择适当的声明周期释放
        super.onPause();
        ijkPlayerView.onPause();
        Jzvd.releaseAllVideos();
        //Change these two variables back
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ijkPlayerView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkPlayerView.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ijkPlayerView.configurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (ijkPlayerView.handleVolumeKey(keyCode)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
