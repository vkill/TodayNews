package com.kx.todaynews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kx.todaynews.adapter.ArticleTabCommentsAdapter;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.bean.article.CommentsAndDetailBean;
import com.kx.todaynews.bean.article.TextDetailInfo;
import com.kx.todaynews.net.YZNetClient;
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.widget.emoji.CommentDialog;
import com.kx.todaynews.widget.emoji.EmojiActivity;
import com.kx.todaynews.widget.webview.ArticleDetailWebView;

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

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String GROUPID = "GROUPID";

    // @BindView(R.id.webView)
    ArticleDetailWebView webView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.listView)
    ListView listView;
    private  ArticleTabCommentsAdapter mCommentsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
       // this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);
        webView = new ArticleDetailWebView(this);
        RetrofitUrlManager.getInstance().putDomain("a3", YZNetClient.HOST_A3);
        webView.setOnWebViewImageClickListener(imageUrl -> ToastUtils.showToast("图片地址 =     " + imageUrl));
        String groupId = getIntent().getStringExtra(GROUPID);
        getArticleDetailData("6604400736325337604");

    }

    private void getArticleDetailData(String groupId) {

        Disposable subscribe2 = Observable.zip(getArticleDetail(groupId), getTabComments(groupId), new BiFunction<TextDetailInfo, ArticleTabCommentsBean, CommentsAndDetailBean>() {
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
                        TextDetailInfo textDetailInfo = commentsAndDetailBean.getTextDetailInfo();
                        ArticleTabCommentsBean articleTabCommentsBean = commentsAndDetailBean.getArticleTabCommentsBean();
                        if (textDetailInfo !=null){
                            String content = textDetailInfo.getData().getContent();
                            int size = textDetailInfo.getData().getImage_detail().size();
                            for (int i = size - 1; i >= 0; i--) {
                                String xx = "&index=" + i;
                                content = content.replace(xx, " ");
                            }
                            if (mCommentsAdapter ==null){
                                mCommentsAdapter = new ArticleTabCommentsAdapter(ArticleDetailActivity.this);
                                listView.setAdapter(mCommentsAdapter);
                            }
                            listView.addHeaderView(webView);
                            webView.loadHtmlStringData(content);
                            webView.setOnPageFinishedListener(() -> {
                                if (articleTabCommentsBean!=null){
                                    ArrayList<ArticleTabCommentsBean.DataBean> data = articleTabCommentsBean.getData();
                                        mCommentsAdapter.setTabCommentsData(data);
                                }
                            });
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
    }
    private Observable<TextDetailInfo> getArticleDetail(String groupId){
        return  YZNetClient.getInstance().get(Api.class).getArticleDetail(groupId, groupId, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    private Observable<ArticleTabCommentsBean> getTabComments(String groupId){
        return  YZNetClient.getInstance().get(Api.class).getTabComments(groupId, groupId, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    CommentDialog commentDialog ;
    @OnClick(R.id.rl_comment)
    public void showDialog(){
     // startActivity(new Intent(this, EmojiActivity.class));

        commentDialog = new CommentDialog("优质评论将会被优先展示", new CommentDialog.SendListener() {
            @Override
            public void sendComment(String inputText) {
                Toast.makeText(getApplicationContext(), inputText, Toast.LENGTH_SHORT).show();
            }
        });
        commentDialog .show(getSupportFragmentManager(), "comment");
    }
}
