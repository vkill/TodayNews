package com.kx.todaynews.module.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.article.ArticleReplyListBean;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.widget.emoji.EmoJiUtils;

/**
 * @author Administrator
 */
public class ArticleReplyListFragmentAdapter extends BaseQuickAdapter<ArticleReplyListBean.DataBeanX.DataBean,BaseViewHolder> {


    public ArticleReplyListFragmentAdapter(int layoutResId) {
        super(layoutResId);
    }
    @Override
    protected void convert(BaseViewHolder holder, ArticleReplyListBean.DataBeanX.DataBean item) {
        Glide.with(mContext).load(item.getUser().getAvatar_url()).transform(new GlideCircleTransform(mContext)).into((ImageView) holder.getView(R.id.user_avatar));
        ((TextView)holder.getView(R.id.user_name)).setText(String.format("%s",item.getUser().getName()));
      //  ((TextView)holder.getView(R.id.user_verified_reason)).setText(String.format("%s",item.getUser().getName()));
//        holder.userVerifiedReason.setText(String.format("%s",dataBean.getVerified_reason()));
//        holder.diggCount.setText(String.format("%s",dataBean.getDigg_count()));
        holder.getView(R.id.user_verified_reason).setVisibility(View.GONE);
        String text = item.getText();
        ((TextView)holder.getView(R.id.reply_content)).setText(EmoJiUtils.parseEmoJi(holder.getView(R.id.reply_content),mContext,text));
        ((TextView)holder.getView(R.id.create_time)).setText(String.format("%s", TyDateUtils.getFriendlytimeByTime(item.getCreate_time())));
//        holder.createTime.setText(String.format("%s", TyDateUtils.getFriendlytimeByTime(dataBean.getCreate_time())));
    }
}
