package com.kx.todaynews.adapter.provider;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.adapter.NewsListAdapter;
import com.kx.todaynews.bean.HotContent;

/**
 * @author ChayChan
 * @description: 纯文本新闻
 * @date 2018/3/22  14:36
 */
public class TextNewsItemProvider extends BaseNewsItemProvider {


    @Override
    public int viewType() {
        return NewsListAdapter.TYPE_TEXT;
    }

    @Override
    public int layout() {
        return R.layout.item_hot_data_text;
    }

    @Override
    protected void setData(BaseViewHolder helper, HotContent news,int position) {
         //由于文本消息的逻辑目前已经在基类中封装，所以此处无须写
        //定义此类是提供文本消息的ItemProvider
    }
}
