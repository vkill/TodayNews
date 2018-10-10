package com.kx.todaynews.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.kx.todaynews.AndroidApplication;


/**
 * @author Administrator
 */
public class ToastUtils {
    private static Toast toast ;
    public static  void showToast(@NonNull String message){
        if (toast ==null){
            toast = Toast.makeText(AndroidApplication.getContext(),message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
    public static  void showToast(@NonNull String message, @Nullable Context context){
        if (toast ==null){
            toast = Toast.makeText(context,message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
}
