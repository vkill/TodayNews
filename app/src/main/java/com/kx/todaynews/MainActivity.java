package com.kx.todaynews;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kx.todaynews.adapter.HotDataAdapter;
import com.kx.todaynews.bean.TextDetailInfo;
import com.kx.todaynews.net.YZNetClient;
import com.kx.todaynews.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.tips)
    TextView tips;
    @BindView(R.id.webView)
    WebView webView;
    private long lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 9));
    private long locTime = System.currentTimeMillis();
    private Gson mGson = new Gson();
    private HotDataAdapter mHotDataAdapter;

    // TODO: 2018/9/20  Android仿今日头条详情页实现   大问题    WebView  + ListView  WebView中的图片点击问题
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mHotDataAdapter = new HotDataAdapter(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycleView.setLayoutManager(linearLayoutManager);
        // recycleView.setAdapter(mHotDataAdapter);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
       // webSettings.setBuiltInZoomControls(true); // 显示放大缩小
       // webSettings.setSupportZoom(true); // 可以缩放
        webView.setWebViewClient(new MyWebViewClient());
        webView.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");
        getHotData();
        //  tips.startAnimation(AnimationUtils.loadAnimation(MainActivity.this,R.anim.scale_aniation));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                lastTime = Long.valueOf((System.currentTimeMillis() + "").substring(0, 9));
                getHotData();
            }
        });
    }

    //  @Query("min_behot_time") long min_behot_time ,
    //  @Query("last_refresh_sub_entrance_interval") long last_refresh_sub_entrance_interval ,
    //  @Query("_rticket") long _rticket,
    //  @Query("ts") long ts,
    //  @Query("loc_time") long loc_time,
    //  需要传递的参数   _rticket  当前时间 精确到毫秒
    // ts    loc_time   last_refresh_sub_entrance_interval     min_behot_time  精确到秒
    //  &resolution=720*1344&dpi=320&_rticket=1537233213   989&ts=1537233214&
    private void getHotData() {

//        Disposable subscribe = YZNetClient.getInstance().get(Api.class).getHotData("news_hot",
//                lastTime, Long.valueOf((System.currentTimeMillis() + "").substring(0, 9)),  System.currentTimeMillis(),Long.valueOf((System.currentTimeMillis() + "").substring(0, 9))
//              //  , System.currentTimeMillis()
//                , "720*1344", "320"
        Disposable subscribe = YZNetClient.getInstance().get(Api.class).getArticleDetail("6603220675295445512")
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TextDetailInfo>() {
                    @Override
                    public void accept(TextDetailInfo hotBean) throws Exception {
                        refreshLayout.setRefreshing(false);
//                        HotBean.TipsBean tipsBean = hotBean.getTips();
//                        tips.setText(TextUtils.isEmpty(tipsBean.getDisplay_info()) ? "暂无更新休息一会" : tipsBean.getDisplay_info());
//                        List<HotBean.DataBean> data = hotBean.getData();
//                        if (data!=null && data.size()>0){
//                            List<HotContent> hotContents = new ArrayList<>();
//                            HotContent hotContent ;
//                            for (HotBean.DataBean dataBean: data) {
//                                String content = dataBean.getContent();
//                                hotContent = mGson.fromJson(content,HotContent.class);
//                                hotContents.add(hotContent);
//                            }
//                            mHotDataAdapter.setHotDatas(hotContents);
//                        }
//                        showTipsAnimation();
                        //  bytedance://large_image?url=
                        String content = hotBean.getData().getContent();
                        String replace = content.replace("bytedance://large_image?url=", "")
                                .replace("class=\"image\"","class=\"img\"")
                                .replace("%3A",":")
                                .replace("%2F","/")
                                ;
                        for (int i = 0; i <= 12; i++) {
                            replace = replace.replace("&index=" +i,"");
                        }
                        LogUtils.e(replace);
                        webView.loadDataWithBaseURL(null, replace, "text/html", "utf-8", null);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        LogUtils.e(throwable.toString());
                        refreshLayout.setRefreshing(false);
                    }
                });
    }

    private void showTipsAnimation() {
        tips.setVisibility(View.VISIBLE);
        int height = tips.getHeight();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) recycleView.getLayoutParams();
        layoutParams.topMargin = height;
        recycleView.setLayoutParams(layoutParams);
        recycleView.postDelayed(new Runnable() {
            @Override
            public void run() {
                ValueAnimator valueAnimator = ValueAnimator.ofInt(height, 0);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        int animatedValue = (int) animation.getAnimatedValue();
                        layoutParams.topMargin = animatedValue;
                        recycleView.setLayoutParams(layoutParams);
                    }
                });
                valueAnimator.setDuration(500);
                valueAnimator.start();
                tips.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.scale_aniation));
                tips.setVisibility(View.INVISIBLE);
            }
        }, 1500);
    }
    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.e(url);
            imgReset();//重置webview中img标签的图片大小
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
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
    public static class JavaScriptInterface {

        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            Log.i("TAG", "响应点击事件!");
            LogUtils.e("响应点击事件!  图片地址 =     "  +  img);
            Intent intent = new Intent();
           // intent.putExtra("image", img);
           // intent.setClass(context, MainActivity.class);//BigImageActivity查看大图的类，自己定义就好
            //context.startActivity(intent);
        }
    }
}
