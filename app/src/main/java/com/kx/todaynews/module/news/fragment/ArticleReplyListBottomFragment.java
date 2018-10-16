package com.kx.todaynews.module.news.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.article.ArticleReplyListBean;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.contract.IArticleReplyListContract;
import com.kx.todaynews.module.BaseFullBottomSheetFragment;
import com.kx.todaynews.module.adapter.ArticleReplyListFragmentAdapter;
import com.kx.todaynews.presenter.ArticleReplyListPresenter;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.utils.UiUtils;
import com.kx.todaynews.widget.emoji.EmoJiUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ArticleReplyListBottomFragment extends BaseFullBottomSheetFragment<ArticleReplyListPresenter>
        implements IArticleReplyListContract.View {
    private static final String COMMENTBEAN = "COMMENTBEAN";
    @BindView(R.id.title_reply_count)
    TextView titleReplyCount;
    @BindView(R.id.iv_finish)
    ImageView ivFinish;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    ArticleReplyListFragmentAdapter mReplyListAdapter;
    ArticleTabCommentsBean.DataBean.CommentBean commentBean;
    ImageView userAvatar;
    TextView userName;
    TextView diggCount;
    TextView fabulousCount;
    TextView replyContent;
    TextView createTime;
    LinearLayout llIvContainer;
    private int offset = 0;
    private int mTotalNumber;
    long id;
    private BottomSheetBehavior mBehavior;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback
            = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                //  dismiss();
                //设置为收缩状态
                //mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            if (slideOffset < 0.01) {
                //dismiss();
            }
        }
    };

    public static ArticleReplyListBottomFragment getInstance(ArticleTabCommentsBean.DataBean.CommentBean commentBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(COMMENTBEAN, commentBean);
        ArticleReplyListBottomFragment articleReplyListBottomFragment = new ArticleReplyListBottomFragment();
        articleReplyListBottomFragment.setArguments(bundle);
        return articleReplyListBottomFragment;
    }

    @Override
    protected ArticleReplyListPresenter createPresenter() {
        return new ArticleReplyListPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bottom_dialog_article_reply;
    }

    @Override
    protected void initListener() {
        mReplyListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.getMoreArticleReplyLists(id, offset);
            }
        }, recycleView);
    }

    @Override
    protected void initView() {
        mReplyListAdapter = new ArticleReplyListFragmentAdapter(getActivity(), R.layout.item_article_reply_list);
        View headerView = View.inflate(getContext(), R.layout.header_view_article_reply_list, null);
        userAvatar = headerView.findViewById(R.id.user_avatar);
        userName = headerView.findViewById(R.id.user_name);
        diggCount = headerView.findViewById(R.id.digg_count);
        fabulousCount = headerView.findViewById(R.id.fabulous_count);
        replyContent = headerView.findViewById(R.id.reply_content);
        createTime = headerView.findViewById(R.id.create_time);
        llIvContainer = headerView.findViewById(R.id.ll_iv_container);
        mReplyListAdapter.addHeaderView(headerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        recycleView.setAdapter(mReplyListAdapter);
        mReplyListAdapter.setonReplyUserNameClickLintener(userName -> ToastUtils.showToast(userName, getActivity()));
    }

    @Override
    protected void initEventAndData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (commentBean != null) {
                return;
            }
            commentBean = arguments.getParcelable(COMMENTBEAN);
            if (commentBean != null) {
                Glide.with(getContext()).load(commentBean.getUser_profile_image_url()).transform(new GlideCircleTransform(getContext())).into(userAvatar);
                userName.setText(String.format("%s", commentBean.getUser_name()));
                diggCount.setText(String.format("%s", commentBean.getDigg_count()));
                fabulousCount.setText(String.format("%s人赞过", commentBean.getDigg_count()));
                String text = commentBean.getText();
                replyContent.setText(EmoJiUtils.parseEmoJi(replyContent, getContext(), text));
                createTime.setText(String.format("%s", TyDateUtils.getFriendlytimeByTime(commentBean.getCreate_time())));
                id = commentBean.getId();
                mPresenter.getArticleReplyLists(id, offset);
            }
        }
    }

    @OnClick(R.id.iv_finish)
    public void onFinishClick() {
        dismiss();
    }

    @Override
    public void showArticleReplyLists(ArticleReplyListBean articleReplyListBean) {
        offset += 20;
        int totalCount = articleReplyListBean.getData().getTotal_count();
        if (totalCount > 0) {
            titleReplyCount.setText(String.format("%S条回复", totalCount));
        }
        List<String> diggListImages = articleReplyListBean.getDiggListImages();
        if (diggListImages != null) {
            for (String images : diggListImages) {
                ImageView imageView = new ImageView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.width = (int) UiUtils.dp2px(getContext(), 25);
                params.height = (int) UiUtils.dp2px(getContext(), 25);
                params.rightMargin = (int) UiUtils.dp2px(getContext(), 4);
                imageView.setLayoutParams(params);
                Glide.with(getContext()).load(images).transform(new GlideCircleTransform(getContext())).into(imageView);
                llIvContainer.addView(imageView);
            }
        }
        List<ArticleReplyListBean.DataBeanX.DataBean> data = articleReplyListBean.getData().getData();
        mTotalNumber = articleReplyListBean.getData().getTotal_count();
        mReplyListAdapter.setNewData(data);
        //  只有一页数据
        if (mTotalNumber <= 20) {
            mReplyListAdapter.loadMoreEnd();
        } else {
            mReplyListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showMoreArticleReplyLists(ArticleReplyListBean articleReplyListBean) {
        offset += 20;
        List<ArticleReplyListBean.DataBeanX.DataBean> data = articleReplyListBean.getData().getData();
        mReplyListAdapter.addData(data);
        int offset = articleReplyListBean.getData().getOffset();
        mTotalNumber = articleReplyListBean.getData().getTotal_count();
        if (offset % 20 != 0 || (offset == mTotalNumber) || (mReplyListAdapter.getData().size() == mTotalNumber)) {
            mReplyListAdapter.loadMoreEnd();
        } else {
            mReplyListAdapter.loadMoreComplete();
        }
    }

    @Override
    public void showLoadMoreFail() {
        mReplyListAdapter.loadMoreFail();
    }

    @Override
    public void showError() {
        super.showError();
        ToastUtils.showToast("加载出错啦 ");
    }
}
