package com.kx.todaynews.module.photo;

import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.kx.todaynews.AndroidApplication;
import com.kx.todaynews.Api;
import com.kx.todaynews.base.BasePresenter;
import com.kx.todaynews.bean.PhotoListBean;
import com.kx.todaynews.contract.IWelfarePhotoContract;
import com.kx.todaynews.net.NetClient;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class WelfarePhotoPresenter extends BasePresenter<IWelfarePhotoContract.IPhotoView> implements IWelfarePhotoContract.IPhotoPresenter {

    private int mPage = 1;

    @Override
    public void getPhotoData() {
        addRxSubscribe(NetClient.getInstance().get(Api.class).getWelfarePhoto(mPage)
                .compose(mTransformer)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PhotoListBean.ResultsBean>>() {
                    @Override
                    public void accept(List<PhotoListBean.ResultsBean> photoListBean) throws Exception {
                        mView.showPhotoData(photoListBean);
                        mPage++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable.toString());
                        mView.showError();
                    }
                })
        );
    }

    @Override
    public void getMorePhotoData() {
        addRxSubscribe(NetClient.getInstance().get(Api.class).getWelfarePhoto(mPage)
                .compose(mTransformer)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<PhotoListBean.ResultsBean>>() {
                    @Override
                    public void accept(List<PhotoListBean.ResultsBean> resultsBeans) throws Exception {
                        mView.showMorePhotoData(resultsBeans);
                        mPage++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        System.out.println(throwable.toString());
                        mView.showError();
                    }
                })
        );
    }
    /**
     * 统一变换
     */
    private ObservableTransformer<PhotoListBean, List<PhotoListBean.ResultsBean>> mTransformer = new ObservableTransformer<PhotoListBean, List<PhotoListBean.ResultsBean>>() {
        @Override
        public ObservableSource<List<PhotoListBean.ResultsBean>> apply(Observable<PhotoListBean> observable) {
            return observable
                    .flatMap(new Function<PhotoListBean, ObservableSource<List<PhotoListBean.ResultsBean>>>() {
                        @Override
                        public ObservableSource<List<PhotoListBean.ResultsBean>> apply(PhotoListBean photoListBean) throws Exception {
                            List<PhotoListBean.ResultsBean> results = photoListBean.getResults();
                           return  Observable.just(calePhotoSize(results))
                                   .subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread());
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread());

        }
    };
    /**
     * 计算图片分辨率
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static List<PhotoListBean.ResultsBean>  calePhotoSize(List<PhotoListBean.ResultsBean> beans) throws ExecutionException, InterruptedException {
        for (PhotoListBean.ResultsBean resultsBean: beans) {
            // 接口返回的数据是没有宽高参数的，这里设置图片的宽度和高度
            try {
                File file = Glide.with(AndroidApplication.getContext()).load(resultsBean.getUrl())
                        .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
                // First decode with inJustDecodeBounds=true to check dimensions
                final BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                resultsBean.setPixel(options.outWidth + "*" + options.outHeight);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return  beans ;
    }
}
