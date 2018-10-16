package com.kx.todaynews.contract;

import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.base.IBaseView;
import com.kx.todaynews.bean.article.ArticleReplyListBean;

public interface IArticleReplyListContract {
    interface View extends IBaseView {
        /**
         *  ArticleReplyListBean 评论回复数据
         */
        void showArticleReplyLists(ArticleReplyListBean articleReplyListBean);

        void showMoreArticleReplyLists(ArticleReplyListBean articleReplyListBean);

        void showLoadMoreFail();
    }

    interface Presenter extends IBasePresenter<View> {
        /**
         *
         * @param replyId 评论id
         * @param offset  列表偏移量
         */
        void getArticleReplyLists(long replyId, int offset);

        void getMoreArticleReplyLists(long replyId, int offset);
    }
}
