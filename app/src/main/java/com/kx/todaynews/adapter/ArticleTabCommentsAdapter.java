package com.kx.todaynews.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.listener.OnArticleReplyClickListener;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.widget.emoji.EmoJiUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * @author admin
 * @date 2018/9/22
 */
public class ArticleTabCommentsAdapter extends BaseAdapter {
    private ArrayList<ArticleTabCommentsBean.DataBean> mComments = new ArrayList<>();

    private LayoutInflater mLayoutInflater;
    private Context mContext ;
    private ArrayList<Integer> mIntegers;
    Typeface mTypeFace ;
    public ArticleTabCommentsAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context ;
        mTypeFace =Typeface.createFromAsset(mContext.getAssets(),"fonts/roundfont.ttf");
    }

    public void setTabCommentsData(ArrayList<ArticleTabCommentsBean.DataBean> comments) {
        if (comments != null && comments.size() > 0) {
            mComments = comments;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (mComments.size() ==0 ){
            return 0;
        }else {
            return 1 ;
        }
    }

    @Override
    public int getCount() {
        return mComments.size()== 0 ? 1 : mComments.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) ==0 ){
            return mLayoutInflater.inflate(R.layout.item_article_empty_comments, null);
        }
        CommentsHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_article_tab_comments, null);
            holder = new CommentsHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CommentsHolder) convertView.getTag();
        }
        ArticleTabCommentsBean.DataBean.CommentBean dataBean = mComments.get(position).getComment();
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
                    mOnArticleReplyClickListener.onarticlereplyclick(dataBean,position);
                }
            });
        }
        return convertView;
    }

    static class CommentsHolder {
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
