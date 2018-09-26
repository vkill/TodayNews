package com.kx.todaynews.widget.emoji;

/**
 * Created by admin on 2018/9/26.
 */

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;

/** *. * 实现弹出软键盘时 整个布局向上平移,解决遮挡问题 * 在onCreate中添加监听,在onDestroy中remove监听 */
public class CustomGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private Context mContext;
    private View mRootView;
    private View mScrollToView;
    /**
     * * @param context      context
     *  * @param rootView     可以滚动的布局
     * * @param scrollToView 界面上被遮挡的位于最底部的布局(控件)
     *  */    public CustomGlobalLayoutListener(Context context, View rootView, View scrollToView) {
         this.mContext = context;
         this.mRootView = rootView;
         this.mScrollToView = scrollToView;
     }
     @Override
    public void onGlobalLayout() {
         Rect rect = new Rect();
         mRootView.getWindowVisibleDisplayFrame(rect);
         int rootInvisibleHeight = mRootView.getRootView().getHeight() - rect.bottom;
         if (rootInvisibleHeight > 100) {
             int[] location = new int[2];
             mScrollToView.getLocationInWindow(location);
             int scrollHeight = (location[1] + mScrollToView.getHeight()) - rect.bottom;
             mRootView.scrollTo(0, scrollHeight + dp2px(mContext, 30));
         } else {
             mRootView.scrollTo(0, 0);
         }
     }
    private int dp2px(Context context,float dpValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale+0.5f);
    }


}

