package com.kx.todaynews.adapter.provider;

import android.text.TextUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.provider.BaseItemProvider;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.HotContent;
import com.kx.todaynews.utils.TyDateUtils;

/**
 * @description: 将新闻中设置数据公共部分抽取
 */

public abstract class BaseNewsItemProvider extends BaseItemProvider<HotContent,BaseViewHolder> {

    @Override
    public void convert(BaseViewHolder helper, HotContent news, int position) {
        if (TextUtils.isEmpty(news.getTitle())) {
            //如果没有标题，则直接跳过
            return;
        }
        //设置标题、底部作者、评论数、发表时间
        helper.setText(R.id.title, news.getTitle())
                .setText(R.id.media_name, news.getSource())
                .setText(R.id.comment_count,String.format("%s评论",news.getComment_count()))
                .setText(R.id.tv_time, TyDateUtils.getShortTime(news.getBehot_time() * 1000));

       setData(helper, news,position);
    }

     abstract void setData(BaseViewHolder helper, HotContent hotContent,int position);
}

