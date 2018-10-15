package com.kx.todaynews.base;

/**
 * Presenter 基类
 */
public interface IBasePresenter <T extends IBaseView> {
    /**
     * 注入View
     */
    void attachView(T view);

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

}
