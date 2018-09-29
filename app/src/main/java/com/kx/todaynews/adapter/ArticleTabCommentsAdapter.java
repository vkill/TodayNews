package com.kx.todaynews.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.article.ArticleTabCommentsBean;
import com.kx.todaynews.utils.GlideCircleTransform;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.widget.VerticalImageSpan;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by admin on 2018/9/22.
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
        SpannableString spannableString = new SpannableString(text);
        int startIndex= -1;
        int endIndex= -1;
        if (mIntegers ==null){
            mIntegers = new ArrayList<>();
        }else {
            mIntegers.clear();
        }
        int textSize = text.length();
        Drawable drawable = mContext.getResources().getDrawable(R.drawable.jingjing);
        drawable.setBounds(5,0, getTextHeight("测试高度",holder.replyContent)+5, getTextHeight("测试高度",holder.replyContent));
        for (int i = 0; i < textSize; i ++) {
            if (text.charAt(i) == '[') {
                startIndex = i;
                mIntegers.add(startIndex);
            }
            if (text.charAt(i) == ']') {
                endIndex = i;
                mIntegers.add(endIndex);
            }
        }
        int indexSize = mIntegers.size();
        for (int i = 0; i <indexSize; i++) {
            if ( i %2 == 1){
                continue;
            }
            VerticalImageSpan imageSpan = new VerticalImageSpan(drawable);
            spannableString.setSpan(imageSpan, mIntegers.get(i), mIntegers.get(i+1)+1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        holder.replyContent.setText(spannableString);
        holder.createTime.setText(String.format("%s", TyDateUtils.getFriendlytimeByTime(dataBean.getCreate_time())));
        holder.replyCount.setText(String.format("%s回复",dataBean.getReply_count()));

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

    private int getTextHeight(String text,TextView textView){
        TextPaint textPaint = new TextPaint();
        textPaint.setTextSize(textView.getTextSize());
        Paint.FontMetricsInt fontMetricsInt = textPaint.getFontMetricsInt();
        return 10 + fontMetricsInt.descent - fontMetricsInt.ascent;
    }
}
