package com.kx.todaynews.utils;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.kx.todaynews.AndroidApplication;


public class ToastUtils {
    private static Toast toast ;
    public static  void showToast(@NonNull String message){
        if (toast ==null){
            toast = Toast.makeText(AndroidApplication.getContext(),message, Toast.LENGTH_SHORT);
        }
        toast.setText(message);
        toast.show();
    }
}
