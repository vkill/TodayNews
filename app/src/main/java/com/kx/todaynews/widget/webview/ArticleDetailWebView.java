package com.kx.todaynews.widget.webview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.kx.todaynews.R;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.ToastUtils;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by admin on 2018/9/21.
 */
public class ArticleDetailWebView extends WebView {
    /**
     * 进度条
     */
    private ProgressBar progress_bar_;
    private  ArrayList<String> imageLists  = new ArrayList<>();

    /**
     *  给Html数据添加的css 样式   定义了  字体大小   行高  颜色
     *  参考链接 ： https://blog.csdn.net/zi413293813/article/details/18144057
     */
    // TODO: 解决字体过小的问题，自定义一个样式文件，通过WebView去加载
    public final static String CSS_STYLE ="<style>* {font-size:17.5px; }p {color:#000000;}</style>";

    public ArticleDetailWebView(Context context) {
        this(context, null);
    }
    /**
     * 不能直接调用this(context, attrs,0),最后style是0的话，会导致无法响应点击动作。
     * 但是如果直接把最后一位写成 com.android.internal.R.attr.webViewStyle 编译时会弹出错误提示，原因：
     * You cannot access id's of com.android.internal.R at compile time, but you can access the
     * defined internal resources at runtime and get the resource by name.
     * You should be aware that this is slower than direct access and there is no guarantee.
     */
    public ArticleDetailWebView(Context context, AttributeSet attrs) {
        //this(context, attrs, com.android.internal.R.attr.webViewStyle);
        this(context, attrs, Resources.getSystem().getIdentifier("webViewStyle","attr","android"));
    }

    public ArticleDetailWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        WebSettings webSettings =getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        //    WebView加载网页不显示图片解决办法   开启混合模式
        if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
           // webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LogUtils.e("newProgress = "  + newProgress);
            }
        });
       setWebViewClient(new MyWebViewClient());
       addJavascriptInterface(new JavaScriptInterface(), "imagelistner");

        if (!isInEditMode()) {
            initView(context);
        }
    }
    /**
     * Description: 初始化界面元素
     * Created by KAKA on 16/1/20 09:43
     *  LayoutParams中第2个参数表示的是progress_bar的高度大小
     */
    private void initView(final Context context) {
        progress_bar_ = new ProgressBar(context,null, android.R.attr.progressBarStyleHorizontal);
        progress_bar_.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 20, 0, 0));
        // 添加drawable
        Drawable drawable = context.getResources().getDrawable(R.drawable.progress_bar_states);
        progress_bar_.setProgressDrawable(drawable);
        this.addView(progress_bar_);
    }

    public void loadHtmlStringData(String htmlData){
          // TODO:    对接口返回的数据进行处理
                htmlData = htmlData.replace("bytedance://large_image?url=", "")
                .replace("class=\"image\"","class=\"img\"")
                .replace("%3A",":")
                .replace("href","src")
                .replace("<a class=\"image\"","<img class=\"image\"")
                .replace("<a class=\"img\"","<img class=\"img\"")
                .replace("</a>","</img>")
                .replace("%2F","/");

        // java读取html文件，截取<header>标签和<header>中的内容
        String start = "<header>";
        String end = "</header>";
        int startIndex = htmlData.indexOf(start) + start.length();
        int endIndex = htmlData.indexOf(end);
        imageLists = getAllImageUrlFromHtml(htmlData);
        // 截取Html数据中的title数据
       // htmlData = htmlData.substring(endIndex,htmlData.length());
        loadDataWithBaseURL(null, CSS_STYLE+htmlData, "text/html", "utf-8", null);
    }
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webview中img标签的图片大小
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
            if (mOnPageFinishedListener!=null){
                mOnPageFinishedListener.onPageLoadedFinish();
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            // 跳过证书判断        访问 https 网站  因为无证书会导致白屏bug
            handler.proceed();
        }
        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) { // Handle the
            ToastUtils.showToast("加载失败," + description);
            goBack() ;
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
        }
    }
    /**
     * Description: 重写webChromeClient类
     * Created by KAKA on 16/1/19 22:40
     */
    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progress_bar_.setVisibility(GONE);
            } else {
                if (progress_bar_.getVisibility() == GONE) {
                    progress_bar_.setVisibility(VISIBLE);
                }
                progress_bar_.setProgress(newProgress);
            }
            super.onProgressChanged(view,newProgress);
        }

        /**
         * Description: 获取页面的标题
         */
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view,title);
        }
    }
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progress_bar_.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progress_bar_.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
        scrollTo(0,t);
    }

    /**x
     * 禁止WebView左右滑动
     */
    @Override
    public void scrollTo(int x, int y) {
        super.scrollTo(x, y);
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.width = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");

        loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('p'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var p = objs[i];   " +
                "    p.style.font-size = 50px;" +
                "}" +
                "})()");

    }

    /**
     *   WebView中的图片点击问题
     */
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }
    public  class JavaScriptInterface {


        private JavaScriptInterface() {
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            if (mListener !=null){
                mListener.onImageClickCallBack(img,imageLists);
            }
        }
    }
    /***
     * 获取页面所有图片对应的地址对象，
     * 例如 <img src="http://sc1.hao123img.com/data/f44d0aab7bc35b8767de3c48706d429e" />
     */
    // 获取img标签正则
    private static final String IMAGE_URL_REGEX = "<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>";

    private ArrayList<String> getAllImageUrlFromHtml(String html) {
        Matcher matcher = Pattern.compile(IMAGE_URL_REGEX).matcher(html);
        ArrayList<String> imgUrlList = new ArrayList<>();
        while (matcher.find()) {
            int count = matcher.groupCount();
            if (count >= 1) {
                imgUrlList.add(matcher.group(1).replace(" ",""));
            }
        }
        return imgUrlList;
    }
    private  boolean xx = false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!xx){
            xx = true;
            if (mOnPageFinishedListener!=null){
                mOnPageFinishedListener.onPageLoadedFinish();
            }
        }
    }

    public onWebViewImageClickListener mListener;

    public void setOnWebViewImageClickListener(onWebViewImageClickListener listener) {
        mListener = listener;
    }

    private onPageFinishedListener mOnPageFinishedListener;

    public void setOnPageFinishedListener(onPageFinishedListener onPageFinishedListener) {
        mOnPageFinishedListener = onPageFinishedListener;
    }
}
