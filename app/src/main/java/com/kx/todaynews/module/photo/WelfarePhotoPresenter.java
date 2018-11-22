package com.kx.todaynews.module.photo;

import android.graphics.BitmapFactory;

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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class WelfarePhotoPresenter extends BasePresenter<IWelfarePhotoContract.IPhotoView> implements IWelfarePhotoContract.IPhotoPresenter {

    private int mPage = 1;

    @Override
    public void getPhotoData() {
        addRxSubscribe(NetClient.getInstance().get(Api.class).getWelfarePhoto(mPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PhotoListBean>() {
                    @Override
                    public void accept(PhotoListBean photoListBean) throws Exception {
                        mView.showPhotoData(photoListBean.getResults());
                        mPage++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                        System.out.println(throwable.toString());
                    }
                })
        );
    }

    @Override
    public void getMorePhotoData() {
        addRxSubscribe(NetClient.getInstance().get(Api.class).getWelfarePhoto(mPage)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PhotoListBean>() {
                    @Override
                    public void accept(PhotoListBean photoListBean) throws Exception {
                        mView.showMorePhotoData(calePhotoSize(photoListBean.getResults()));
                        mPage++;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showError();
                    }
                })
        );
    }
    /**
     * 统一变换
     */
//    private ObservableTransformer<PhotoListBean.ResultsBean, List<PhotoListBean.ResultsBean>> mTransformer = new ObservableTransformer<PhotoListBean.ResultsBean, List<PhotoListBean.ResultsBean>>() {
//        @Override
//        public ObservableSource<List<PhotoListBean.ResultsBean>> apply(Observable<PhotoListBean.ResultsBean> observable) {
//            return observable
//                    .observeOn(Schedulers.io())
//                    // 接口返回的数据是没有宽高参数的，所以这里设置图片的宽度和高度，速度会慢一点
//                    su
//                    .filter(new Predicate<PhotoListBean.ResultsBean>() {
//                        @Override
//                        public boolean test(PhotoListBean.ResultsBean photoListBean) throws Exception {
//                            try {
//                                photoListBean.setPixel(calePhotoSize(photoListBean.getUrl()));
//                                return true;
//                            } catch (ExecutionException e) {
//                                e.printStackTrace();
//                                return false;
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                                return false;
//                            }
//                        }
//                    })
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .toList().compose(new SingleTransformer<List<PhotoListBean.ResultsBean>, List<PhotoListBean.ResultsBean>>() {
//                        @Override
//                        public SingleSource<List<PhotoListBean.ResultsBean>> apply(Single<List<PhotoListBean.ResultsBean>> upstream) {
//                            return upstream;
//                        }
//                    });
//        }
//    };
    /**
     * 计算图片分辨率
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public static List<PhotoListBean.ResultsBean>  calePhotoSize(List<PhotoListBean.ResultsBean> beans) throws ExecutionException, InterruptedException {
        for (PhotoListBean.ResultsBean resultsBean: beans) {
            File file = Glide.with(AndroidApplication.getContext()).load(resultsBean.getUrl())
                    .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            // First decode with inJustDecodeBounds=true to check dimensions
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            resultsBean.setPixel(options.outWidth + "*" + options.outHeight);
        }
        return  beans ;
    }
}
