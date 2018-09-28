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

import java.util.ArrayList;
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
//        ImageSpan span = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
//        spannable.setSpan(span, i, i + 7, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        //  查询   emoji 表情图顺序的接口
        //  http://ic.snssdk.com/user/expression/config/?iid=44267707161&device_id=57548705831&ac=wifi&channel=tengxun2&aid=13&app_name=news_article&version_code=691&version_name=6.9.1&device_platform=android&ab_version=425530%2C511489%2C512678%2C486952%2C442428%2C500130%2C504794%2C494121%2C499728%2C478963%2C496464%2C239097%2C500092%2C170988%2C493249%2C480607%2C374117%2C495946%2C478532%2C489312%2C501963%2C509852%2C513538%2C276206%2C453560%2C435216%2C459650%2C459993%2C511225%2C500386%2C416055%2C510641%2C392484%2C495897%2C378451%2C471406%2C510754%2C513728%2C508932%2C509307%2C512915%2C468954%2C271178%2C424178%2C326524%2C326532%2C476036%2C514892%2C496389%2C345191%2C504889%2C512336%2C512047%2C504723%2C513201%2C514035%2C455643%2C424177%2C214069%2C513805%2C507002%2C442255%2C514737%2C512958%2C489509%2C280449%2C281299%2C513401%2C511104%2C325618%2C508560%2C514708%2C514821%2C512807%2C498551%2C509887%2C508594%2C386889%2C498375%2C397995%2C467514%2C513891%2C512007%2C444464%2C506751%2C514991%2C261578%2C403270%2C491728%2C491265%2C293032%2C457481%2C502679%2C510535%2C491255%2C507368&ab_client=a1%2Cc4%2Ce1%2Cf1%2Cg2%2Cf7&ab_feature=94563%2C102749&abflag=3&ssmix=a&device_type=Redmi+Note+4X&device_brand=xiaomi&language=zh&os_api=24&os_version=7.0&openudid=30f2074ddcee24da&manifest_version_code=691&resolution=1080*1920&dpi=480&update_version_code=69111&_rticket=1537718641955&plugin=0&fp=irT_JzPqFlKtFlD_PlU1F2mIFSF1&rom_version=miui_v9_v9.6.2.0.ncfcnfd&ts=1537718641&as=a2459b8aa1c7fb09b76341&mas=00f6e15477087c8bb3ab2150c2bdb0d427548780aa02e846ea

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
