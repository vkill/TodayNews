package com.kx.todaynews;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kx.todaynews.adapter.HotDataAdapter;
import com.kx.todaynews.bean.HotBean;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.net.YZNetClient;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.TYDateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tips)
    TextView tips;
    private long lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 10));
    private long locTime = System.currentTimeMillis();
    private int session_refresh_idx = 1;
    private Gson mGson = new Gson();
    private HotDataAdapter mHotDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHotDataAdapter = new HotDataAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        recycleView.setLayoutManager(linearLayoutManager);
         recycleView.setAdapter(mHotDataAdapter);
        getHotData();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getHotData();
            }
        });
        mHotDataAdapter.setOnItemClickListener(groupId -> {
            Intent intent = new Intent(this,ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailActivity.GROUPID,groupId);
            startActivity(intent);
        });
    }

    //  @Query("min_behot_time") long min_behot_time ,
    //  @Query("last_refresh_sub_entrance_interval") long last_refresh_sub_entrance_interval ,
    //  @Query("_rticket") long _rticket,
    //  @Query("ts") long ts,
    //  @Query("loc_time") long loc_time,
    //  需要传递的参数   _rticket  当前时间 精确到毫秒
    // ts    loc_time   last_refresh_sub_entrance_interval     min_behot_time  精确到秒
    //  &resolution=720*1344&dpi=320&_rticket=1537233213   989&ts=1537233214&
    private void getHotData() {

        Disposable subscribe = YZNetClient.getInstance().get(Api.class).getHotData("news_hot",session_refresh_idx,mHotDataAdapter.getListCount()+17,
                lastTime, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)),  System.currentTimeMillis(),Long.valueOf((System.currentTimeMillis() + "").substring(0, 10))
              //  , System.currentTimeMillis()
                , "720*1344", "320"      )                                                           // 6603220675295445512    6603478830382318094
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotBean>() {
                    @Override
                    public void accept(HotBean hotBean) throws Exception {
                        lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 10));
                        session_refresh_idx ++;
                        refreshLayout.setRefreshing(false);
                        HotBean.TipsBean tipsBean = hotBean.getTips();
                        tips.setText(TextUtils.isEmpty(tipsBean.getDisplay_info()) ? "暂无更新休息一会" : tipsBean.getDisplay_info());
                        List<HotBean.DataBean> data = hotBean.getData();
                        if (data!=null && data.size()>0){
                            List<HotContent> hotContents = new ArrayList<>();
                            HotContent hotContent ;
                            for (HotBean.DataBean dataBean: data) {
                                String content = dataBean.getContent();
                                hotContent = mGson.fromJson(content,HotContent.class);
                                hotContents.add(hotContent);
                            }
                            mHotDataAdapter.setHotDatas(hotContents);
                        }
                        showTipsAnimation();
//                        String content = hotBean.getData().getContent();
//                        int size = hotBean.getData().getImage_detail().size();
//                        for (int i = size-1; i >= 0; i--) {
//                            String xx = "&index=" +i;
//                            content = content.replace(xx," ");
//                        }
//                        webView.loadHtmlStringData(content);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        refreshLayout.setRefreshing(false);
                    }
                });
    }
    private void showTipsAnimation() {
        tips.setVisibility(View.VISIBLE);
        int height = tips.getHeight();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) recycleView.getLayoutParams();
        layoutParams.topMargin = height;
        recycleView.setLayoutParams(layoutParams);
        recycleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(height, 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        layoutParams.topMargin = animatedValue;
                        recycleView.setLayoutParams(layoutParams);
                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
                tips.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_aniation));
                tips.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }
}
