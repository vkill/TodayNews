package com.kx.todaynews.contract;

import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.base.IBaseView;
import com.kx.todaynews.bean.HotBean;

/**
 * Created by admin on 2018/10/18.
 */
public interface INewsListContract  {

    interface  INewsListView extends IBaseView {

        void showNewsList(HotBean hotBean) ;

        void showMoreNewsList(HotBean hotBean) ;
    }

    interface  INewsListPresenter extends IBasePresenter<INewsListView> {

         void getNewsList(String category) ;
    }
}
