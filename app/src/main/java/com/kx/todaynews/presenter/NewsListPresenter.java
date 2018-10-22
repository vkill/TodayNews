package com.kx.todaynews.presenter;

import com.kx.todaynews.Api;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.bean.HotBean;
import com.kx.todaynews.contract.INewsListContract;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.utils.PreUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2018/10/18.
 */
public class NewsListPresenter extends BasePresenter<INewsListContract.INewsListView> implements INewsListContract.INewsListPresenter {

    private long lastTime ;
    private long locTime = System.currentTimeMillis();
    private int session_refresh_idx = 1;


    @Override
    public void getNewsList(String channelCode) {
        //读取对应频道下最后一次刷新的时间戳
        lastTime = PreUtils.getLong(channelCode,0);
        if (lastTime == 0){
            //如果为空，则是从来没有刷新过，使用当前时间戳
            lastTime = System.currentTimeMillis() / 1000;
        }
        addRxSubscribe(NetClient.getInstance().get(Api.class)
            .getNewsList(channelCode, session_refresh_idx, 0,
                    lastTime, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis(), Long.valueOf((System.currentTimeMillis() + "").substring(0, 10))
                    //  , System.currentTimeMillis()
                    , "720*1344", "320")
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<HotBean>() {
                @Override
                public void accept(HotBean hotBean) throws Exception {
                    session_refresh_idx++;
                    mView.showNewsList(hotBean);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    mView.showError();
                }
            })
        );
    }


}
