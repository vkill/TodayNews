package com.kx.todaynews.contract;

import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.base.IBaseView;
import com.kx.todaynews.bean.ImageListDetailBean;

/**
 * Created by admin on 2018/11/6.
 */
public interface IImageListDetailContract {

    interface  IImageListDetailView extends IBaseView {

        void showImageListDetail(ImageListDetailBean imageListDetailBean) ;
    }

    interface  IImageListDetailPresenter extends IBasePresenter<IImageListDetailContract.IImageListDetailView> {

        void getImageListDetail(String groupId) ;
    }
}
