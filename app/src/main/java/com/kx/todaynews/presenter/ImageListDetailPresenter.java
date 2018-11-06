package com.kx.todaynews.presenter;

import com.kx.todaynews.Api;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.bean.ImageListDetailBean;
import com.kx.todaynews.contract.IImageListDetailContract;
import com.kx.todaynews.net.NetClient;
import com.kx.todaynews.utils.ToastUtils;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 2018/11/6.
 */
public class ImageListDetailPresenter extends BasePresenter<IImageListDetailContract.IImageListDetailView> implements IImageListDetailContract.IImageListDetailPresenter {
    @Override
    public void getImageListDetail(String groupId) {

        addRxSubscribe(getImageListDetails(groupId)
            .subscribe(new Consumer<ImageListDetailBean>() {
                @Override
                public void accept(ImageListDetailBean imageListDetailBean) throws Exception {
                    mView.showImageListDetail(imageListDetailBean);
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    ToastUtils.showToast("请求出错：" + throwable.toString());
                }
            })
        );


    }





    private Observable<ImageListDetailBean> getImageListDetails(String groupId) {
        return NetClient.getInstance().get(Api.class).getImageListDetail(groupId, groupId, Long.valueOf((System.currentTimeMillis() + "").substring(0, 10)), System.currentTimeMillis())
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
