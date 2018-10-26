package com.kx.todaynews.module;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.ArticleTabCommentsAdapter;
import com.kx.todaynews.base.BaseFragment;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.contract.ICommentsContract;
import com.kx.todaynews.module.news.fragment.ArticleReplyListBottomFragment;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.presenter.CommentsPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class CommentsFragment extends BaseFragment<CommentsPresenter> implements ICommentsContract.ICommentsView {

    public static final String GROUP_ID = "group_id";
    @BindView(R.id.recycleView)
    RecyclerView  mCommentsRecycleView;
    private String mGroupId;
    private ArticleTabCommentsAdapter mCommentsAdapter;
    private int mTotalNumber;

    public static CommentsFragment getInstance(String groupId){
        CommentsFragment commentsFragment = new CommentsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(GROUP_ID,groupId);
        commentsFragment.setArguments(bundle);
        return commentsFragment;
    }


    @Override
    protected void initView(View rootView) {

    }

    @Override
    protected CommentsPresenter createPresenter() {
        return new CommentsPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_recyclerview;
    }

    @Override
    protected void initEventAndData() {
        mCommentsAdapter = new ArticleTabCommentsAdapter(mActivity, R.layout.item_article_tab_comments);
        mCommentsRecycleView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mCommentsRecycleView.setAdapter(mCommentsAdapter);
        mCommentsAdapter.setEmptyView(R.layout.pager_no_comment, mCommentsRecycleView);
        mCommentsAdapter.setHeaderAndEmpty(true);
        RetrofitUrlManager.getInstance().putDomain("a3", NetClient.HOST_A3);
    }

    @Override
    protected void initListener() {
        mCommentsAdapter.setOnArticleReplyClickListener((commentBean) -> {
            ArticleReplyListBottomFragment bottomSheetFragment = ArticleReplyListBottomFragment.getInstance(commentBean);
            bottomSheetFragment.show(mActivity.getSupportFragmentManager(),ArticleReplyListBottomFragment.class.getSimpleName());
        });
        mCommentsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getTabComments(mGroupId,true);
            }
        },mCommentsRecycleView);
    }

    @Override
    protected void loadData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            mGroupId = arguments.getString(GROUP_ID);
            mPresenter.getTabComments(mGroupId,false);
        }
    }

    @Override
    public void showTabComments(ArticleTabCommentsBean tabCommentsBean ,boolean isLoadMore) {
        ArrayList<ArticleTabCommentsBean.DataBean> data = tabCommentsBean.getData();
        if (isLoadMore){
            mCommentsAdapter.addData(data);
        }else {
            mCommentsAdapter.setNewData(data);
        }
        mTotalNumber = tabCommentsBean.getTotal_number();
        if (mCommentsAdapter.getData().size() == mTotalNumber) {
            mCommentsAdapter.loadMoreEnd();
        } else {
            mCommentsAdapter.loadMoreComplete();
        }
    }
}
