package com.kx.todaynews.contract;

import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.base.IBaseView;
import com.kx.todaynews.bean.ArticleCategory;

/**
 * Created by admin on 2018/10/27.
 */
public interface IArticleCategoryContract {
    interface  IArticleCategoryView extends IBaseView {

        void showArticleCategory(ArticleCategory articleCategory) ;
    }

    interface  IArticleCategoryPresenter extends IBasePresenter<IArticleCategoryContract.IArticleCategoryView> {

        void getArticleCategory() ;
    }
}
