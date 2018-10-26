package com.kx.todaynews.contract;

import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.base.IBaseView;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;

public interface ICommentsContract {

    interface ICommentsView  extends IBaseView{

        void  showTabComments(ArticleTabCommentsBean tabCommentsBean ,boolean isLoadMore);
    }

    interface  ICommentsPresenter  extends IBasePresenter<ICommentsView>{

        void  getTabComments(String groupId,boolean isLoadMore);
    }
}
