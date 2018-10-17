package com.kx.todaynews.module.news;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kx.todaynews.ImageBrowserActivity;
import com.kx.todaynews.R;
import com.kx.todaynews.widget.webview.ArticleDetailWebView;
import com.kx.todaynews.widget.webview.onWebViewImageClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @description: 新闻详情页头部
 */

public class NewsDetailHeaderView extends FrameLayout {

    private static final String NICK = "chaychan";

    @BindView(R.id.tvTitle)
    TextView mTvTitle;

    @BindView(R.id.ll_info)
    public LinearLayout mLlInfo;

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;

    @BindView(R.id.tv_author)
    TextView mTvAuthor;

    @BindView(R.id.tv_time)
    TextView mTvTime;

    @BindView(R.id.wv_content)
    ArticleDetailWebView mWvContent;

    private Context mContext;

    public NewsDetailHeaderView(Context context) {
        this(context, null);
    }

    public NewsDetailHeaderView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewsDetailHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.header_news_detail, this);
        ButterKnife.bind(this, this);
    }

    public void setDetail(String detail,LoadWebListener listener) {
        mWebListener = listener;

//        mTvTitle.setText(detail.title);
//
//        if (detail.media_user == null) {
//            //如果没有用户信息
//            mLlInfo.setVisibility(GONE);
//        } else {
//            if (!TextUtils.isEmpty(detail.media_user.avatar_url)){
//                GlideUtils.loadRound(mContext, detail.media_user.avatar_url, mIvAvatar);
//            }
//            mTvAuthor.setText(detail.media_user.screen_name);
//            mTvTime.setText(com.chaychan.news.utils.DateUtils.getShortTime(detail.publish_time * 1000L));
//        }
//
//        if (TextUtils.isEmpty(detail.content))
//            mWvContent.setVisibility(GONE);

        mWvContent.getSettings().setJavaScriptEnabled(true);//设置JS可用

        String htmlPart1 = "<!DOCTYPE HTML html>\n" +
                "<head><meta charset=\"utf-8\"/>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no\"/>\n" +
                "</head>\n" +
                "<body>\n" +
                "<style> \n" +
                "img{width:100%!important;height:auto!important}\n" +
                " </style>";
        String htmlPart2 = "</body></html>";

       // String html = htmlPart1 + detail.content + htmlPart2;
        mWvContent.setOnWebViewImageClickListener((imageUrl, imageLists) -> {
            if (mListener!=null){
                mListener.onImageClickCallBack(imageUrl,imageLists);
            }
        });
        mWvContent.loadHtmlStringData(detail);
        mWvContent.setOnPageFinishedListener(() -> {
            System.out.println("webView加载完毕");
            if (mWebListener != null){
                mWebListener.onLoadFinished();
            }
        });
    }
    private LoadWebListener mWebListener;

    /**页面加载的回调*/
    public interface LoadWebListener{
       void onLoadFinished();
    }
    public onWebViewImageClickListener mListener;

    public void setOnWebViewImageClickListener(onWebViewImageClickListener listener) {
        mListener = listener;
    }
}
