package com.kx.todaynews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.kx.todaynews.bean.HotBean;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.bean.TextDetailInfo;
import com.kx.todaynews.net.YZNetClient;
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.webview.ArticleDetailWebView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;

public class ArticleDetailActivity extends AppCompatActivity {
    public static final String GROUPID = "GROUPID";

   // @BindView(R.id.webView)
    ArticleDetailWebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        webView = findViewById(R.id.webView);
        RetrofitUrlManager.getInstance().putDomain("a3",YZNetClient.HOST_A3);
        webView.setOnWebViewImageClickListener(imageUrl -> ToastUtils.showToast("图片地址 =     "  +  imageUrl));
        String groupId = getIntent().getStringExtra(GROUPID);
        getArticleDetailData(groupId);
    }
    private void getArticleDetailData(String groupId) {
                  Disposable subscribe = YZNetClient.getInstance().get(Api.class).getArticleDetail(groupId,groupId,Long.valueOf((System.currentTimeMillis() + "").substring(0, 9)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextDetailInfo>() {
                    @Override
                    public void accept(TextDetailInfo hotBean) throws Exception {
                        String content = hotBean.getData().getContent();
                        int size = hotBean.getData().getImage_detail().size();
                        for (int i = size-1; i >= 0; i--) {
                            String xx = "&index=" +i;
                            content = content.replace(xx," ");
                        }
                        webView.loadHtmlStringData(content);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //refreshLayout.setRefreshing(false);
                    }
                });
    }
}
