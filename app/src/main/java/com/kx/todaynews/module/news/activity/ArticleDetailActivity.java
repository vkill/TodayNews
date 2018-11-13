package com.kx.todaynews.module.news.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kx.todaynews.Api;
import com.kx.todaynews.ImageBrowserActivity;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.ArticleTabCommentsAdapter;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.bean.article.CommentsAndDetailBean;
import com.kx.todaynews.bean.article.TextDetailInfo;
import com.kx.todaynews.module.news.NewsDetailHeaderView;
import com.kx.todaynews.module.news.fragment.ArticleReplyListBottomFragment;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.widget.SoftKeyBoardListener;
import com.kx.todaynews.widget.emoji.CommentDialog;
import com.kx.todaynews.widget.loadinglayout.LoadingLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

/**
 * @author Administrator
 */
public class ArticleDetailActivity extends AppCompatActivity {
    public static final String GROUPID = "GROUPID";

  //  ArticleDetailWebView webView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.articleDetailRecyclerView)
    RecyclerView articleDetailRecyclerView;
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    private ArticleTabCommentsAdapter mCommentsAdapter;
    private int offset = 0;
    private int mTotalNumber;
    protected NewsDetailHeaderView mHeaderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
      //  webView = new ArticleDetailWebView(this);
        mHeaderView = new NewsDetailHeaderView(this);
        mCommentsAdapter = new ArticleTabCommentsAdapter(ArticleDetailActivity.this, R.layout.item_article_tab_comments);
        articleDetailRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        articleDetailRecyclerView.setAdapter(mCommentsAdapter);
        mCommentsAdapter.addHeaderView(mHeaderView);
        mCommentsAdapter.setEmptyView(R.layout.pager_no_comment, articleDetailRecyclerView);
        mCommentsAdapter.setHeaderAndEmpty(true);
        RetrofitUrlManager.getInstance().putDomain("a3", NetClient.HOST_A3);
        mHeaderView.setOnWebViewImageClickListener((imageUrl, imageLists) -> {
            ImageBrowserActivity.start(this, imageUrl, imageLists);
        });
        String groupId = getIntent().getStringExtra(GROUPID);
        getArticleDetailData(groupId);
        mCommentsAdapter.setOnArticleReplyClickListener((commentBean) -> {
            ArticleReplyListBottomFragment bottomSheetFragment = ArticleReplyListBottomFragment.getInstance(commentBean);
            bottomSheetFragment.show(getSupportFragmentManager(), ArticleReplyListBottomFragment.class.getSimpleName());
        });
        mCommentsAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                Disposable subscribe = getTabComments(groupId, offset).subscribe(new Consumer<ArticleTabCommentsBean>() {
                    @Override
                    public void accept(ArticleTabCommentsBean articleTabCommentsBean) throws Exception {
                        offset += 20;
                        ArrayList<ArticleTabCommentsBean.DataBean> data = articleTabCommentsBean.getData();
                        mCommentsAdapter.addData(data);
                        mTotalNumber = articleTabCommentsBean.getTotal_number();
                        if (mCommentsAdapter.getData().size() == mTotalNumber) {
                            mCommentsAdapter.loadMoreEnd();
                        } else {
                            mCommentsAdapter.loadMoreComplete();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mCommentsAdapter.loadMoreFail();
                    }
                });
            }
        }, articleDetailRecyclerView);


        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                LogUtils.e("keyBoardShow = " + height);
                if (commentDialog != null) {
                    commentDialog.setSoftKeyBoardHeight(height);
                }
            }

            @Override
            public void keyBoardHide(int height) {
                LogUtils.e("keyBoardHide = " + height);
                // if (commentDialog!=null)
                // commentDialog.setSoftKeyBoardHeight(0);
            }
        });

    }

    private void getArticleDetailData(String groupId) {

        Disposable subscribe2 = Observable.zip(getArticleDetail(groupId), getTabComments(groupId, offset), new BiFunction<TextDetailInfo, ArticleTabCommentsBean, CommentsAndDetailBean>() {
            @Override
            public CommentsAndDetailBean apply(TextDetailInfo textDetailInfo, ArticleTabCommentsBean articleTabCommentsBean) throws Exception {
                CommentsAndDetailBean commentsAndDetailBean = new CommentsAndDetailBean();
                commentsAndDetailBean.setTextDetailInfo(textDetailInfo);
                commentsAndDetailBean.setArticleTabCommentsBean(articleTabCommentsBean);
                return commentsAndDetailBean;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CommentsAndDetailBean>() {
                    @Override
                    public void accept(CommentsAndDetailBean commentsAndDetailBean) throws Exception {
                        offset += 20;
                        TextDetailInfo textDetailInfo = commentsAndDetailBean.getTextDetailInfo();
                        ArticleTabCommentsBean articleTabCommentsBean = commentsAndDetailBean.getArticleTabCommentsBean();
                        if (textDetailInfo != null) {
                            String content = textDetailInfo.getData().getContent();
                            int size = textDetailInfo.getData().getImage_detail().size();
                            for (int i = size - 1; i >= 0; i--) {
                                String xx = "&index=" + i;
                                content = content.replace(xx, " ");
                            }
                            if (articleTabCommentsBean != null) {
                                ArrayList<ArticleTabCommentsBean.DataBean> data = articleTabCommentsBean.getData();
                                mCommentsAdapter.setNewData(data);
                                mTotalNumber = articleTabCommentsBean.getTotal_number();
                                //  只有一页数据
                                if (mCommentsAdapter.getData().size() == mTotalNumber) {
                                    mCommentsAdapter.loadMoreEnd();
                                } else {
                                    mCommentsAdapter.loadMoreComplete();
                                }
                            }
                            mHeaderView.setDetail(content, () -> {
                              //  ToastUtils.showToast("隐藏刷新界面");
                                loadingLayout.showContentView();
                            });
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        ToastUtils.showToast("请求出错 = " + throwable.toString());
                        loadingLayout.showDataError();
                    }
                });
    }

    private Observable<TextDetailInfo> getArticleDetail(String groupId) {
        return NetClient.getInstance().get(Api.class).getArticleDetail(groupId, groupId, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<ArticleTabCommentsBean> getTabComments(String groupId, int offset) {
        return NetClient.getInstance().get(Api.class).getTabComments(offset, groupId, groupId, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    CommentDialog commentDialog;

    @OnClick(R.id.rl_comment)
    public void showDialog() {
        // startActivity(new Intent(this, EmojiActivity.class));

        commentDialog = new CommentDialog("优质评论将会被优先展示", new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                Toast.makeText(getApplicationContext(), inputText, Toast.LENGTH_SHORT).show();
            }
        });
        commentDialog.show(getSupportFragmentManager(), "comment");
    }
}
