package com.kx.todaynews.presenter;

import com.kx.todaynews.Api;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.contract.ICommentsContract;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CommentsPresenter extends BasePresenter<ICommentsContract.ICommentsView> implements ICommentsContract.ICommentsPresenter {
    private int offset = 0;
    @Override
    public void getTabComments(String groupId ,boolean isLoadMore) {

        addRxSubscribe(getTabComments(groupId, offset)
            .subscribe(new Consumer<ArticleTabCommentsBean>() {
                @Override
                public void accept(ArticleTabCommentsBean tabCommentsBean) throws Exception {
                    offset += 20;
                    mView.showTabComments(tabCommentsBean,isLoadMore);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ToastUtils.showLong("请求出错 = " + throwable.toString());
                }
            })
        );

    }
    private Observable<ArticleTabCommentsBean> getTabComments(String groupId, int offset) {
        return NetClient.getInstance().get(Api.class).getTabComments(offset, groupId, groupId, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
