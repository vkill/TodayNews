package com.kx.todaynews.base;

/**
 * View 基类
 */
public interface IBaseView {

    /**
     * Use night mode
     *
     * @param isNightMode if is night mode
     */
    void useNightMode(boolean isNightMode);

    /**
     * showNormal
     */
    void showNormal();

    /**
     * Show error
     */
    void showError();

    /**
     * Show loading
     */
    void showLoading();

    /**
     * Reload
     */
    void reload();

    /**
     * Show toast
     */
    void showToast(String message);

    /**
     * Show error message
     */
    void showErrorMsg(String errorMsg);

}
