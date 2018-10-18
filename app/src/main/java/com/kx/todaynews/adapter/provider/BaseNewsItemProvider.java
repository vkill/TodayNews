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

    private String mChannelCode;

    public BaseNewsItemProvider(String channelCode) {
        mChannelCode = channelCode;
    }

    @Override
    public void convert(BaseViewHolder helper, HotContent news, int i) {
        if (TextUtils.isEmpty(news.getTitle())) {
            //如果没有标题，则直接跳过
            return;
        }
        //设置标题、底部作者、评论数、发表时间
        helper.setText(R.id.title, news.getTitle())
                .setText(R.id.media_name, news.getSource())
                .setText(R.id.comment_count,String.format("%s评论",news.getComment_count()))
                .setText(R.id.tv_time, TyDateUtils.getShortTime(news.getBehot_time() * 1000));
//
//        //根据情况显示置顶、广告和热点的标签
//        int position = helper.getAdapterPosition();
//        String[] channelCodes = UIUtils.getStringArr(R.array.channel_code);
//        boolean isTop = position == 0 && mChannelCode.equals(channelCodes[0]); //属于置顶
//        boolean isHot = news.hot == 1;//属于热点新闻
//        boolean isAD = !TextUtils.isEmpty(news.tag) ? news.tag.equals(Constant.ARTICLE_GENRE_AD) : false;//属于广告新闻
//        boolean isMovie = !TextUtils.isEmpty(news.tag) ? news.tag.equals(Constant.TAG_MOVIE) : false;//如果是影视
//        helper.setVisible(R.id.tv_tag, isTop || isHot || isAD);//如果是上面任意一个，显示标签
//        helper.setVisible(R.id.tv_comment_num, !isAD);//如果是广告，则隐藏评论数
//
//        String tag = "";
//        if (isTop) {
//            tag = UIUtils.getString(R.string.to_top);
//            helper.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_F96B6B));
//        } else if (isHot) {
//            tag = UIUtils.getString(R.string.hot);
//            helper.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_F96B6B));
//        } else if (isAD) {
//            tag = UIUtils.getString(R.string.ad);
//            helper.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_3091D8));
//        } else if (isMovie) {
//            //如果是影视
//            tag = UIUtils.getString(R.string.tag_movie);
//            helper.setTextColor(R.id.tv_tag, UIUtils.getColor(R.color.color_F96B6B));
//        }
//        helper.setText(R.id.tv_tag, tag);
//
//
       setData(helper, news);
    }

     abstract void setData(BaseViewHolder helper, HotContent hotContent);
}

