package com.kx.todaynews.contract;

import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.base.IBaseView;
import com.kx.todaynews.bean.PhotoListBean;

import java.util.List;

public interface IWelfarePhotoContract {

    interface IPhotoView extends IBaseView{

        void showPhotoData(List<PhotoListBean.ResultsBean> beans);

        void showMorePhotoData(List<PhotoListBean.ResultsBean> beans);
    }

    interface IPhotoPresenter extends IBasePresenter<IWelfarePhotoContract.IPhotoView>{

        void  getPhotoData();

        void  getMorePhotoData();
    }
}
