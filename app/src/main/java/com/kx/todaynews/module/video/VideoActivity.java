package com.kx.todaynews.module.video;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.KeyEvent;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kx.todaynews.Api;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.ArticleTabCommentsAdapter;
import com.kx.todaynews.base.BaseActivity;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.contract.ICommentsContract;
import com.kx.todaynews.module.news.fragment.ArticleReplyListBottomFragment;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.presenter.CommentsPresenter;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.UiUtils;
import com.kx.todaynews.widget.behavior.CtrlLinearLayoutManager;
import com.kx.todaynews.widget.behavior.ZoomVideoHeader;

import java.util.ArrayList;
import java.util.Random;
import java.util.zip.CRC32;

import butterknife.BindView;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class VideoActivity extends BaseActivity<CommentsPresenter> implements ICommentsContract.ICommentsView {
    public static final String ITEM_ID = "item_id";
    public static final String VIDEO_ID = "video_id";
    public static final String THUMBIMAGEVIEW = "thumbImageView";
    public static final String TITLETEXTVIEW = "titleTextView";
    @BindView(R.id.videoplayer)
    JzvdStd videoPlayer;
    @BindView(R.id.recyclerView)
    RecyclerView mCommentsRecycleView;
    @BindView(R.id.zoomVideoHeader)
    ZoomVideoHeader zoomVideoHeader;
    private String mItemId;
    private String mVideoId;
    private int offset = 0;
    private ArticleTabCommentsAdapter mCommentsAdapter;
    private int mTotalNumber;

    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video);
//
//    //    CommentsFragment instance = CommentsFragment.getInstance(mItemId);
////        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
////        ft.add(R.id.fl_container,instance,CommentsFragment.class.getSimpleName()).commit();
//
//    }
    private Observable<ArticleTabCommentsBean> getTabComments(String groupId, int offset) {
        return NetClient.getInstance().get(Api.class).getTabComments(offset, groupId, groupId, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected CommentsPresenter createPresenter() {
        return new CommentsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_video;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected void initEventAndData() {
        mItemId = getIntent().getStringExtra(ITEM_ID);
        mVideoId = getIntent().getStringExtra(VIDEO_ID);
        Glide.with(this).load(getIntent().getStringExtra(THUMBIMAGEVIEW))
                .into(videoPlayer.thumbImageView);
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;

        CtrlLinearLayoutManager manager = new CtrlLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        manager.setScrollEnabled(false);
        mCommentsRecycleView.setLayoutManager(manager);
        zoomVideoHeader.setRecyclerView(mCommentsRecycleView);
        mCommentsAdapter = new ArticleTabCommentsAdapter(this, R.layout.item_article_tab_comments);
        mCommentsRecycleView.setAdapter(mCommentsAdapter);
        mCommentsAdapter.setEmptyView(R.layout.pager_no_comment, mCommentsRecycleView);
        mCommentsAdapter.setHeaderAndEmpty(true);
        RetrofitUrlManager.getInstance().putDomain("a3", NetClient.HOST_A3);
        mPresenter.getTabComments(mItemId, false);
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
                            videoPlayer.setUp(mMediaSmooth, getIntent().getStringExtra(TITLETEXTVIEW), JzvdStd.SCREEN_WINDOW_NORMAL);
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
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.toString());
                    }
                });
    }

    @Override
    protected void initListener() {
        mCommentsAdapter.setOnArticleReplyClickListener((commentBean) -> {
            if (zoomVideoHeader.isOnTop()){
                ArticleReplyListBottomFragment bottomSheetFragment = ArticleReplyListBottomFragment.getInstance(commentBean);
                bottomSheetFragment.show(getSupportFragmentManager(), ArticleReplyListBottomFragment.class.getSimpleName(),
                        UiUtils.getScreenHeight(this)- UiUtils.dp2px(this,200)-UiUtils.getStatusBarHeight());
            }
        });
        mCommentsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getTabComments(mItemId, true);
            }
        }, mCommentsRecycleView);
    }

    @Override
    public void showTabComments(ArticleTabCommentsBean tabCommentsBean, boolean isLoadMore) {
        ArrayList<ArticleTabCommentsBean.DataBean> data = tabCommentsBean.getData();
        if (isLoadMore) {
            mCommentsAdapter.addData(data);
        } else {
            mCommentsAdapter.setNewData(data);
        }
        mTotalNumber = tabCommentsBean.getTotal_number();
        if (mCommentsAdapter.getData().size() == mTotalNumber) {
            mCommentsAdapter.loadMoreEnd();
        } else {
            mCommentsAdapter.loadMoreComplete();
        }
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
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() { //选择适当的声明周期释放
        super.onPause();
        Jzvd.releaseAllVideos();
        //Change these two variables back
        Jzvd.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR;
        Jzvd.NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

}
