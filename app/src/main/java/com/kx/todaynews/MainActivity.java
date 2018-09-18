package com.kx.todaynews;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.LinearLayout;
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
import retrofit2.http.Query;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.ll_header)
    LinearLayout llHeader;
    private long lastTime = Long.valueOf((System.currentTimeMillis()+"").substring(0,9));
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
        Disposable subscribe = YZNetClient.getInstance().get(Api.class).getHotData(
                System.currentTimeMillis(), lastTime, System.currentTimeMillis(),lastTime,System.currentTimeMillis(),"720*1344","320"
        ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotBean>() {
                    @Override
                    public void accept(HotBean hotBean) throws Exception {
                        refreshLayout.setRefreshing(false);
                        HotBean.TipsBean tipsBean = hotBean.getTips();
                        //  tips.setVisibility(View.VISIBLE);
                        lastTime = Long.valueOf((System.currentTimeMillis()+"").substring(0,9));
                        tips.setText(TextUtils.isEmpty(tipsBean.getDisplay_info())? "暂无更新休息一会":tipsBean.getDisplay_info());
                        showTipsAnimation();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.toString());
                        refreshLayout.setRefreshing(false);
                        lastTime = System.currentTimeMillis();
                    }
                });
    }

    private void showTipsAnimation() {
        tips.measure(0, 0);
        int height = tips.getMeasuredHeight();
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(llHeader, "translationY", -height, 0);
        // 设置移动时间
        objectAnimator1.setDuration(10);
//      开始动画
        objectAnimator1.start();
        llHeader.postDelayed(new Runnable() {
            @Override
            public void run() {
                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(llHeader, "translationY", 0, -height);
                // 设置移动时间
                objectAnimator.setDuration(700);
//      开始动画
                objectAnimator.start();
            }
        },1000);
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(height,0);
//        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator animation) {
//                int animatedValue = (int) animation.getAnimatedValue();
//                System.out.println("animatedValue =   " + animatedValue);
//                tips.setHeight(animatedValue);
//            }
//        });
//
//        valueAnimator.setDuration(1000);
//        valueAnimator.start();
    }
}
