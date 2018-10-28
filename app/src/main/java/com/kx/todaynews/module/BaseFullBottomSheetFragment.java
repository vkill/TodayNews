package com.kx.todaynews.module;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.kx.todaynews.R;
import com.kx.todaynews.base.IBasePresenter;
import com.kx.todaynews.base.IBaseView;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFullBottomSheetFragment <P extends IBasePresenter>extends BottomSheetDialogFragment implements IBaseView{
    protected P mPresenter;
    private Unbinder unBinder;

    /**
     *   当我们手动调用 BottomSheetDialogFragment#show(FragmentManager manager, String tag)方法,方法调用顺序
     *
     * show(FragmentManager manager, String tag);
     * onCreate();
     * onCreateDialog(Bundle savedInstanceState);
     * onCreateView();
     * onViewCreated();
     * onStart();
     * mDialog.show();
     * onResume();
     */
    /**
     * 顶部向下偏移量
     */
    private int topOffset = 0;
    private BottomSheetBehavior<FrameLayout> behavior;
    private boolean isFirstInit = true;
    /**
     *  BottomSheetDialogFragment 的高度
     */
    private int mHeight ;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getContext() == null) {
            return super.onCreateDialog(savedInstanceState);
        }
        return new BottomSheetDialog(getContext(), R.style.ArticleReplyFragmentAnim);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mHeight <=0){
            this.mHeight = UiUtils.getScreenHeight(getContext());
        }
        mPresenter = createPresenter();
        if (mPresenter!=null){
            mPresenter.attachView(this);
        }
        initListener();
        initEventAndData();
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.e(mHeight+"");
        if (isFirstInit){
            BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
            /**
             * 去掉设置默认背景为透明色。
             */
            dialog.getWindow().findViewById(R.id.design_bottom_sheet).setBackgroundResource(android.R.color.transparent);
            FrameLayout bottomSheet = dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomSheet.getLayoutParams();
                layoutParams.height = mHeight;
                behavior = BottomSheetBehavior.from(bottomSheet);
                // 初始为展开状态
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            isFirstInit = false;
        }
    }

    /**
     *  设置BottomSheetDialogFragment的高度,默认填充屏幕
     *  set the height for BottomSheetDialogFragment
     * @param bottomSheetDialogHeight  the height for BottomSheetDialogFragment
     */
    public void show(FragmentManager fragmentManager,String tag,int bottomSheetDialogHeight){
        this.mHeight = bottomSheetDialogHeight;
        super.show(fragmentManager,tag);
    }
    public void show(FragmentManager fragmentManager,String tag){
        super.show(fragmentManager,tag);
    }
    /**
     * 获取屏幕高度
     *
     * @return height
     */
    private int getHeight() {
        int height = 1920;
        if (getContext() != null) {
            WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
            Point point = new Point();
            if (wm != null) {
                // 使用Point已经减去了状态栏高度
                wm.getDefaultDisplay().getSize(point);
                height = point.y - getTopOffset();
            }
        }
        return height;
    }
    public int getTopOffset() {
        return topOffset;
    }
    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
    }
    public BottomSheetBehavior<FrameLayout> getBehavior() {
        return behavior;
    }
    /**
     * 创建Presenter
     * @return T Presenter
     */
    protected abstract P createPresenter();
    /**
     * 初始化Listener
     */
    protected abstract void initListener();
    /**
     * 初始化数据
     */
    protected abstract void initEventAndData();
    /**
     * 初始化View
     */
    protected abstract void initView();
    /**
     * @return 布局id
     */
    protected abstract int getLayoutId();

    @Override
    public void useNightMode(boolean isNightMode) {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void reload() {

    }

    @Override
    public void showToast(String message) {

    }

    @Override
    public void showErrorMsg(String errorMsg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder!=null){
            unBinder.unbind();
        }
    }
}