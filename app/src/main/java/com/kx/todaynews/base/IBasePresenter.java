package com.kx.todaynews.base;

import io.reactivex.disposables.Disposable;

/**
 * Presenter 基类
 */
public interface IBasePresenter <V extends IBaseView> {
    /**
     * 注入View
     */
    void attachView(V view);

    /**
     * 回收View
     */
    void detachView();

    /**
     * Get night mode state
     *
     * @return if is night mode
     */
//    boolean getNightModeState();
    /**
     * Add rxBing subscribe manager
     * @param disposable Disposable
     */
    void addRxSubscribe(Disposable disposable);

}
