package com.kx.todaynews.presenter;


import com.kx.todaynews.Api;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.bean.article.ArticleReplyDiggListBean;
import com.kx.todaynews.bean.article.ArticleReplyListBean;
import com.kx.todaynews.contract.IArticleReplyListContract;
import com.kx.todaynews.net.NetClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ArticleReplyListPresenter extends BasePresenter<IArticleReplyListContract.View> implements IArticleReplyListContract.Presenter {

    @Override
    public void getArticleReplyLists(long replyId, int offset) {
        addRxSubscribe(Observable.zip(getArticleReplyList(replyId,offset), getArticleReplyDiggLists(replyId), new BiFunction<ArticleReplyListBean, ArticleReplyDiggListBean, ArticleReplyListBean>() {
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
                mView.showArticleReplyLists(articleReplyListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showError();
            }
        }));

    }

    @Override
    public void getMoreArticleReplyLists(long replyId, int offset) {
        addRxSubscribe(getArticleReplyList(replyId, offset).subscribe(new Consumer<ArticleReplyListBean>() {
            @Override
            public void accept(ArticleReplyListBean articleReplyListBean) throws Exception {
                mView.showMoreArticleReplyLists(articleReplyListBean);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                mView.showLoadMoreFail();
            }
        }));
    }

    private Observable<ArticleReplyListBean> getArticleReplyList(long replyId, int offset) {
        return NetClient.getInstance().get(Api.class).getArticleReplyList(
                offset,replyId+"", Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
    private Observable<ArticleReplyDiggListBean> getArticleReplyDiggLists(long diggId) {
        return NetClient.getInstance().get(Api.class).getArticleReplyDiggList(
                diggId + "", Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
