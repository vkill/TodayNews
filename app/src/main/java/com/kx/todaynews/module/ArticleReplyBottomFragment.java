package com.kx.todaynews.module;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
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
import com.kx.todaynews.Api;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.article.ArticleReplyDiggListBean;
import com.kx.todaynews.bean.article.ArticleReplyListBean;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.module.adapter.ArticleReplyListFragmentAdapter;
import com.kx.todaynews.net.YZNetClient;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.utils.UiUtils;
import com.kx.todaynews.widget.emoji.EmoJiUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 */
public class ArticleReplyBottomFragment extends BottomSheetDialogFragment {
    private static final String COMMENTBEAN = "COMMENTBEAN";
    /**
     * The bottom sheet is dragging.
     */
    public static final int STATE_DRAGGING = 1;

    /**
     * The bottom sheet is settling.
     */
    public static final int STATE_SETTLING = 2;

    /**
     * The bottom sheet is expanded.
     */
    public static final int STATE_EXPANDED = 3;

    /**
     * The bottom sheet is collapsed.
     */
    public static final int STATE_COLLAPSED = 4;

    /**
     * The bottom sheet is hidden.
     */
    public static final int STATE_HIDDEN = 5;
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

    public static ArticleReplyBottomFragment getInstance(ArticleTabCommentsBean.DataBean.CommentBean commentBean) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(COMMENTBEAN, commentBean);
        ArticleReplyBottomFragment articleReplyBottomFragment = new ArticleReplyBottomFragment();
        articleReplyBottomFragment.setArguments(bundle);
        return articleReplyBottomFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(), R.style.ArticleReplyFragmentAnim);
        View view = View.inflate(getContext(), R.layout.bottom_dialog_article_reply, null);
        ButterKnife.bind(this, view);
        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.width=UiUtils.getScreenWidth(getContext());
        layoutParams.height=UiUtils.getScreenHeight(getContext());
        view.setLayoutParams(layoutParams);
        mBehavior = BottomSheetBehavior.from(view);
        mBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        dialog.setContentView(view);
        dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(android.R.color.transparent));

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
        mReplyListAdapter.setonReplyUserNameClickLintener(userName -> ToastUtils.showToast(userName,getActivity()));
        mReplyListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Disposable subscribe = getArticleReplyLists(id, offset).subscribe(new Consumer<ArticleReplyListBean>() {
                    @Override
                    public void accept(ArticleReplyListBean articleReplyListBean) throws Exception {
                        offset += 20;
                        List<ArticleReplyListBean.DataBeanX.DataBean> data = articleReplyListBean.getData().getData();
                        mReplyListAdapter.addData(data);
                        int offset = articleReplyListBean.getData().getOffset();
                        mTotalNumber = articleReplyListBean.getData().getTotal_count();
                        if (offset % 20 != 0 || (offset == mTotalNumber) || (mReplyListAdapter.getData().size() == mTotalNumber)) {
                            mReplyListAdapter.loadMoreEnd();
                        }else {
                            mReplyListAdapter.loadMoreComplete();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mReplyListAdapter.loadMoreFail();
                    }
                });
            }
        },recycleView);
        return dialog;
    }


    @Override
    public void onStart() {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //mBehavior.setSkipCollapsed(true);
        // mBehavior.setPeekHeight(0);
    }

    @Override
    public void onResume() {
        super.onResume();
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (commentBean != null) {
                return;
            }
            commentBean = arguments.getParcelable(COMMENTBEAN);
            if (commentBean != null) {
                Glide.with(getContext()).load(commentBean.getUser_profile_image_url()).transform(new GlideCircleTransform(getContext())).into(userAvatar);
                userName.setText(String.format("%s",commentBean.getUser_name()));
                diggCount.setText(String.format("%s",commentBean.getDigg_count()));
                fabulousCount.setText(String.format("%s人赞过",commentBean.getDigg_count()));
                String text = commentBean.getText();
                replyContent.setText(EmoJiUtils.parseEmoJi( replyContent,getContext(),text));
                createTime.setText(String.format("%s", TyDateUtils.getFriendlytimeByTime(commentBean.getCreate_time())));
                id = commentBean.getId();
                Disposable subscribe = Observable.zip(getArticleReplyLists(id,offset), getArticleReplyDiggLists(id), new BiFunction<ArticleReplyListBean, ArticleReplyDiggListBean, ArticleReplyListBean>() {
                    @Override
                    public ArticleReplyListBean apply(ArticleReplyListBean articleReplyListBean, ArticleReplyDiggListBean articleReplyDiggListBean) throws Exception {
                        if (articleReplyDiggListBean!=null){
                            List<ArticleReplyDiggListBean.DataBeanX.DataBean> data = articleReplyDiggListBean.getData().getData();
                            if (data!=null && data.size()>0 ){
                                List<String> strings = new ArrayList<>();
                                for ( ArticleReplyDiggListBean.DataBeanX.DataBean dataBean :  data) {
                                    if (strings.size()==3){
                                        break;
                                    }
                                    strings.add(dataBean.getAvatar_url());
                                }
                                articleReplyListBean.setDiggListImages(strings);
                            }
                        }
                        return articleReplyListBean;
                    }
                }).subscribe(new Consumer<ArticleReplyListBean>() {
                    @Override
                    public void accept(ArticleReplyListBean articleReplyListBean) throws Exception {
                        int totalCount = articleReplyListBean.getData().getTotal_count();
                        if (totalCount > 0) {
                            titleReplyCount.setText(String.format("%S条回复", totalCount));
                        }
                        List<String> diggListImages = articleReplyListBean.getDiggListImages();
                        if (diggListImages!=null){
                            for (String images: diggListImages) {
                                ImageView imageView = new ImageView(getContext());
                                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                                params.width = (int) UiUtils.dp2px(getContext(),25);
                                params.height = (int) UiUtils.dp2px(getContext(),25);
                                params.rightMargin = (int) UiUtils.dp2px(getContext(),4);
                                imageView.setLayoutParams(params);
                                Glide.with(getContext()).load(images).transform(new GlideCircleTransform(getContext())).into(imageView);
                                llIvContainer.addView(imageView);
                            }
                        }
                        offset += 20;
                        List<ArticleReplyListBean.DataBeanX.DataBean> data = articleReplyListBean.getData().getData();
                        mTotalNumber = articleReplyListBean.getData().getTotal_count();
                        mReplyListAdapter.setNewData(data);
                        //  只有一页数据
                        if (mTotalNumber<= 20){
                            mReplyListAdapter.loadMoreEnd();
                        }else {
                            mReplyListAdapter.loadMoreComplete();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast("出错啦 " + throwable.toString());
                    }
                });
            }
        }
    }

    private Observable<ArticleReplyListBean> getArticleReplyLists(long replyId,int offset) {
        return YZNetClient.getInstance().get(Api.class).getArticleReplyList(
                offset,replyId+"", Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    private Observable<ArticleReplyDiggListBean> getArticleReplyDiggLists(long diggId) {
        return YZNetClient.getInstance().get(Api.class).getArticleReplyDiggList(
                diggId + "", Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public void doclick(View v) {
        //点击任意布局关闭
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //去掉父布局的背景
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            if (parent != null) {
                parent.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    @OnClick(R.id.iv_finish)
    public void onFinishClick() {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
