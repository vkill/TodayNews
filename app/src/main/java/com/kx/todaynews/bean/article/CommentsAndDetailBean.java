package com.kx.todaynews.bean.article;

/**
 */
public class CommentsAndDetailBean {
    private  ArticleTabCommentsBean mArticleTabCommentsBean;
    private  TextDetailInfo mTextDetailInfo;

    public ArticleTabCommentsBean getArticleTabCommentsBean() {
        return mArticleTabCommentsBean;
    }

    public void setArticleTabCommentsBean(ArticleTabCommentsBean articleTabCommentsBean) {
        mArticleTabCommentsBean = articleTabCommentsBean;
    }

    public TextDetailInfo getTextDetailInfo() {
        return mTextDetailInfo;
    }

    public void setTextDetailInfo(TextDetailInfo textDetailInfo) {
        mTextDetailInfo = textDetailInfo;
    }
}
