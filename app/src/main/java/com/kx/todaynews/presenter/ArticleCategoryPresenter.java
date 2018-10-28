package com.kx.todaynews.presenter;

import com.kx.todaynews.Api;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.bean.ArticleCategory;
import com.kx.todaynews.contract.IArticleCategoryContract;
import com.kx.todaynews.net.NetClient;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2018/10/27.
 */
public class ArticleCategoryPresenter extends BasePresenter<IArticleCategoryContract.IArticleCategoryView> implements IArticleCategoryContract.IArticleCategoryPresenter {

    @Override
    public void getArticleCategory() {
        addRxSubscribe(getArticleCategorys()
                .subscribe(new Consumer<ArticleCategory>() {
                    @Override
                    public void accept(ArticleCategory articleCategory) throws Exception {
                        mView.showArticleCategory(articleCategory);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                })
        );
    }

    private Observable<ArticleCategory> getArticleCategorys() {
        return NetClient.getInstance().get(Api.class).getArticleCategory(
                 Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
