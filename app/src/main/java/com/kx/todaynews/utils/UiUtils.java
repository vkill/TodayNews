package com.kx.todaynews.utils;

import android.content.Context;
import android.content.res.Resources;

import com.kx.todaynews.AndroidApplication;

/**
 * @author Administrator
 */
public class UiUtils {
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static float sp2px(Context context, float sp) {
        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public static Context getContext() {
        return AndroidApplication.getContext();
    }
    /**
     * 得到string.xml中的字符串
     */
    public static String getString(int resId) {
        return getResource().getString(resId);
    }
    /**
     * 得到resources对象
     */
    public static Resources getResource() {
        return getContext().getResources();
    }
}
