package com.kx.todaynews.module.news;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kx.todaynews.Api;
import com.kx.todaynews.ArticleDetailActivity;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.HotDataAdapter;
import com.kx.todaynews.bean.HotBean;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.net.YZNetClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by admin on 2018/10/14.
 */
public class NewsFragment extends Fragment {
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tips)
    TextView tips;
    Unbinder unbinder;
    private long lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 10));
    private long locTime = System.currentTimeMillis();
    private int session_refresh_idx = 1;
    private Gson mGson = new Gson();
    private HotDataAdapter mHotDataAdapter;

    public static NewsFragment getInstance() {
        return new NewsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_news, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHotDataAdapter = new HotDataAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailActivity.GROUPID, groupId);
            startActivity(intent);
        });
    }

    private void getHotData() {
        Disposable subscribe = YZNetClient.getInstance().get(Api.class).getHotData("news_hot", session_refresh_idx, mHotDataAdapter.getListCount() + 17,
                lastTime, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis(), Long.valueOf((System.currentTimeMillis() + "").substring(0, 10))
                //  , System.currentTimeMillis()
                , "720*1344", "320")                                                           // 6603220675295445512    6603478830382318094
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<HotBean>() {
                    @Override
                    public void accept(HotBean hotBean) throws Exception {
                        lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 10));
                        session_refresh_idx++;
                        refreshLayout.setRefreshing(false);
                        HotBean.TipsBean tipsBean = hotBean.getTips();
                        tips.setText(TextUtils.isEmpty(tipsBean.getDisplay_info()) ? "暂无更新休息一会" : tipsBean.getDisplay_info());
                        List<HotBean.DataBean> data = hotBean.getData();
                        if (data != null && data.size() > 0) {
                            List<HotContent> hotContents = new ArrayList<>();
                            HotContent hotContent;
                            for (HotBean.DataBean dataBean : data) {
                                String content = dataBean.getContent();
                                hotContent = mGson.fromJson(content, HotContent.class);
                                // 去掉广告数据
                                if ("广告".equals(hotContent.getLabel())){
                                    continue;
                                }
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
                tips.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.scale_aniation));
                tips.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
