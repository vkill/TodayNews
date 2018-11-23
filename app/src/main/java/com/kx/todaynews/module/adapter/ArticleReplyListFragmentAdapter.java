package com.kx.todaynews.module.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.article.ArticleReplyListBean;
import com.kx.todaynews.utils.TyDateUtils;
import com.kx.todaynews.widget.emoji.EmoJiUtils;


/**
 * @author Administrator
 */
public class ArticleReplyListFragmentAdapter extends BaseQuickAdapter<ArticleReplyListBean.DataBeanX.DataBean,BaseViewHolder> {
    private Context mContext;
    private final RequestOptions mRequestOptions;
    public ArticleReplyListFragmentAdapter(Context context,int layoutResId) {
        super(layoutResId);
        this.mContext = context;
        mRequestOptions   =  RequestOptions.circleCropTransform();
    }
    @Override
    protected void convert(BaseViewHolder holder, ArticleReplyListBean.DataBeanX.DataBean item) {
        Glide.with(mContext).load(item.getUser().getAvatar_url()).apply(mRequestOptions).into((ImageView) holder.getView(R.id.user_avatar));
        holder.setText(R.id.user_name,String.format("%s",item.getUser().getName()))
                .setText(R.id.digg_count,String.format("%s",item.getDigg_count()))
                .setText(R.id.create_time,String.format("%s", TyDateUtils.getFriendlytimeByDate(item.getCreate_time())));
        holder.getView(R.id.user_verified_reason).setVisibility(View.GONE);
        StringBuilder text = new StringBuilder(item.getText());
        ArticleReplyListBean.DataBeanX.DataBean.ReplyToCommentBean replyToComment = item.getReply_to_comment();
        if (replyToComment!=null){
            text.append(" //@"+replyToComment.getUser_name()+":" + replyToComment.getText());
        }
        TextView replyContent = holder.getView(R.id.reply_content);
        SpannableString spannableString = EmoJiUtils.parseEmoJi(replyContent, mContext, text.toString());
        if (replyToComment!=null){
            int startIndex = item.getText().length()+3;
            int endIndex = startIndex + replyToComment.getUser_name().length()+2;
            replyContent.setTag(replyToComment.getUser_name());

            ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ContextCompat.getColor(mContext,R.color.colorPrimary));
            spannableString.setSpan(foregroundColorSpan,startIndex,endIndex, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            ClickableSpan clickableSpan  = new ClickableSpan() {
                //  点击事件的处理
                @Override
                public void onClick(View view) {
                    TextView textView = (TextView) view;
                    if (mLintener!=null){
                        mLintener.onUserNameClick(textView.getTag().toString());
                    }
                }
                // 去掉下划线
                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setUnderlineText(false);
                }
            };
            spannableString.setSpan(clickableSpan, startIndex,endIndex , Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            replyContent.setMovementMethod(LinkMovementMethod.getInstance());
            replyContent.setHighlightColor(Color.parseColor("#36969696"));
        }
        holder.setText(R.id.reply_content,spannableString);
    }
    onReplyUserNameClickLintener  mLintener ;

    public void setonReplyUserNameClickLintener(onReplyUserNameClickLintener lintener) {
        mLintener = lintener;
    }

    public interface onReplyUserNameClickLintener {
        void onUserNameClick(String userName);
    }
}
