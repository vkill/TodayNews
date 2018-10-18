package com.kx.todaynews.module.news;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.NewsListAdapter;
import com.kx.todaynews.base.BaseFragment;
import com.kx.todaynews.bean.HotBean;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.constants.Constant;
import com.kx.todaynews.contract.INewsListContract;
import com.kx.todaynews.module.news.activity.ArticleDetailActivity;
import com.kx.todaynews.presenter.NewsListPresenter;
import com.kx.todaynews.widget.loadinglayout.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by admin on 2018/10/18.
 */
public class NewsListFragment extends BaseFragment<NewsListPresenter> implements INewsListContract.INewsListView {
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    private NewsListAdapter mNewsListAdapter;
    private String mChannelCode;
    private Gson mGson = new Gson();
    @Override
    protected NewsListPresenter createPresenter() {
        return new NewsListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main_news_list;
    }

    @Override
    protected void initView(View rootView) {
        mNewsListAdapter = new NewsListAdapter(getActivity());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(mNewsListAdapter);
    }

    @Override
    protected void initEventAndData() {
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE);
    }

    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        loadingLayout.setOnRetryClickListener(new LoadingLayout.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                loadData();
            }
        });
        mNewsListAdapter.setOnItemClickListener(groupId -> {
            Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
            intent.putExtra(ArticleDetailActivity.GROUPID, groupId);
            startActivity(intent);
        });
    }

    @Override
    protected void loadData() {
        loadingLayout.showLoading();
        mPresenter.getNewsList(mChannelCode);
    }

    @Override
    public void showNewsList(HotBean hotBean) {
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
                if ("广告".equals(hotContent.getLabel())) {
                    continue;
                }
                hotContents.add(hotContent);
            }
            mNewsListAdapter.setHotDatas(hotContents);
            loadingLayout.showContentView();
        }else {
            loadingLayout.showEmpty();
        }
        showTipsAnimation();
    }

    @Override
    public void showMoreNewsList(HotBean hotBean) {

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

}
