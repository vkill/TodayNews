package com.kx.todaynews;

import android.app.Application;
import android.content.Context;

import com.kx.todaynews.net.YZNetClient;
import com.kx.todaynews.utils.BaseConstants;
import com.kx.todaynews.utils.LogUtils;

public class AndroidApplication extends Application {
    public static Context mContext ;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        LogUtils.init();
        BaseConstants.init(this);
        YZNetClient.init(mContext);
    }
    public static Context getContext(){
        return mContext;
    }
}
