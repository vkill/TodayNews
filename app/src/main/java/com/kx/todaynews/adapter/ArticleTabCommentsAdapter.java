package com.kx.todaynews.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.listener.OnArticleReplyClickListener;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.widget.emoji.EmoJiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @author admin
 * @date 2018/9/22
 */
public class ArticleTabCommentsAdapter extends BaseQuickAdapter<ArticleTabCommentsBean.DataBean,ArticleTabCommentsAdapter.CommentsHolder> {

    private LayoutInflater mLayoutInflater;
    private Context mContext ;
    public ArticleTabCommentsAdapter(Context context, @LayoutRes int LayoutRes) {
        super(LayoutRes);
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context ;
    }
    @Override
    public int getItemCount() {
        return super.getItemCount();
    }

    @Override
    protected void convert(CommentsHolder holder, ArticleTabCommentsBean.DataBean itemData) {
        ArticleTabCommentsBean.DataBean.CommentBean dataBean  = itemData.getComment();
        Glide.with(mContext).load(dataBean.getUser_profile_image_url()).transform(new GlideCircleTransform(mContext)).into(holder.userAvatar);
        holder.userName.setText(String.format("%s",dataBean.getUser_name()));
        holder.userVerifiedReason.setText(String.format("%s",dataBean.getVerified_reason()));
        holder.diggCount.setText(String.format("%s",dataBean.getDigg_count()));
        String text = dataBean.getText();
        holder.replyContent.setText(EmoJiUtils.parseEmoJi( holder.replyContent,mContext,text));
        holder.createTime.setText(String.format("%s", TyDateUtils.getFriendlytimeByTime(dataBean.getCreate_time())));
        holder.replyCount.setText(String.format("%s回复",(dataBean.getReply_count()<=0 ? "": dataBean.getReply_count())));
        if (mOnArticleReplyClickListener!=null){
            holder.replyCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnArticleReplyClickListener.onarticlereplyclick(dataBean);
                }
            });
        }
    }
    static class CommentsHolder extends BaseViewHolder{
        @BindView(R.id.user_avatar)
        ImageView userAvatar;
        @BindView(R.id.user_name)
        TextView userName;
        @BindView(R.id.user_verified_reason)
        TextView userVerifiedReason;
        @BindView(R.id.digg_count)
        TextView diggCount;
        @BindView(R.id.reply_content)
        TextView replyContent;
        @BindView(R.id.create_time)
        TextView createTime;
        @BindView(R.id.reply_count)
        TextView replyCount;
        CommentsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private int getTextHeight(TextView textView){
        Paint.FontMetricsInt fontMetricsInt =  textView.getPaint().getFontMetricsInt();
        return 10 + fontMetricsInt.descent - fontMetricsInt.ascent;
    }
    OnArticleReplyClickListener<ArticleTabCommentsBean.DataBean.CommentBean> mOnArticleReplyClickListener;

    public void setOnArticleReplyClickListener(OnArticleReplyClickListener<ArticleTabCommentsBean.DataBean.CommentBean> onArticleReplyClickListener) {
        mOnArticleReplyClickListener = onArticleReplyClickListener;
    }
}
