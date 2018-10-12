package com.kx.todaynews.widget.webview;

import java.util.ArrayList;

/**
 * Created by admin on 2018/9/21.
 *  WebView中图片点击的回调
 */
public interface onWebViewImageClickListener {
    void onImageClickCallBack(String imageUrl,ArrayList<String> imageLists);
}
