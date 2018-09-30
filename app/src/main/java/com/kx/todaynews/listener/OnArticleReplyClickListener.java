package com.kx.todaynews.listener;

/**
 * @author Administrator
 */
 public interface OnArticleReplyClickListener<T> {
    /**
     *  新闻详情页评论列表回复Listener
     */
    void onarticlereplyclick(T t, int position);
}
