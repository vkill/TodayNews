package com.kx.todaynews.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;

import com.kx.todaynews.AndroidApplication;

import java.io.File;

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

    public static int getStatusBarHeight() {
        //获取status_bar_height资源的ID
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        //根据资源ID获取响应的尺寸值
        return getResources().getDimensionPixelSize(resourceId);
    }

    public static Context getContext() {
        return AndroidApplication.getContext();
    }
    /**
     * 得到string.xml中的字符串
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }
    /**
     * 得到resources对象
     */
    public static Resources getResources() {
        return getContext().getResources();
    }
    /**
     * 安装APK,  修改apk文件的权限为可执行 ，例如chmod ‘777’ file：
     */
//    public final static void install(Context ctx , String apkPathName, boolean checkApk) {
//        //ScreenFilterService.stopService(ctx);
//        if(STR.isEmptyNull(apkPathName)) return ;
//        File.chmod("777", apkPathName); //修改权限
//        String tmp  = apkPathName.toLowerCase();
//        if (checkApk) {
//            if(!FILE.isApk(tmp)) return ;
//        }
//        File file = new File(apkPathName);
//        if (file.exists() && file.canRead()){
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                intent.setDataAndType(FileShareProvider.getUriForFile(ctx, BuildConfig.APPLICATION_ID + ".provider", file),
//                        "application/vnd.android.package-archive");
//            } else {
//                intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
//            }
//            ctx.startActivity(intent);
//        }
//    }
}
