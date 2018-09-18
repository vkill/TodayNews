package com.kx.todaynews;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kx.todaynews.bean.HotBean;
import com.kx.todaynews.net.YZNetClient;
import com.kx.todaynews.utils.LogUtils;

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
    @BindView(R.id.content)
    TextView content;
    private long lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 9));
    private long locTime = System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        System.out.println(System.currentTimeMillis());
        // getHotData();
        //  tips.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale_aniation));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 9));
                getHotData();
            }
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

        Disposable subscribe = YZNetClient.getInstance().get(Api.class).getHotData("news_hot",
                lastTime, Long.valueOf((System.currentTimeMillis() + "").substring(0, 9)),  System.currentTimeMillis(),Long.valueOf((System.currentTimeMillis() + "").substring(0, 9))
              //  , System.currentTimeMillis()
                , "720*1344", "320"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotBean>() {
                    @Override
                    public void accept(HotBean hotBean) throws Exception {
                        refreshLayout.setRefreshing(false);
                        HotBean.TipsBean tipsBean = hotBean.getTips();
                        tips.setText(TextUtils.isEmpty(tipsBean.getDisplay_info()) ? "暂无更新休息一会" : tipsBean.getDisplay_info());
                        showTipsAnimation();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.toString());
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void showTipsAnimation() {
        tips.setVisibility(View.VISIBLE);
        int height = tips.getHeight();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) content.getLayoutParams();
        layoutParams.topMargin = height ;
        content.setLayoutParams(layoutParams);
        content.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(height,0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        layoutParams.topMargin = animatedValue ;
                        content.setLayoutParams(layoutParams);
                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
                tips.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale_aniation));
                tips.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }
}
