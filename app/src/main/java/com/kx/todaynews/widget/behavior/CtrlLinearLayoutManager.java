package com.kx.todaynews.widget.behavior;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * 用来控制recyclerView在透明度为 0 时可否滑动
 */
public class CtrlLinearLayoutManager extends LinearLayoutManager {
    private boolean isScrollEnabled = true;

    public CtrlLinearLayoutManager(Context context) {
        super(context);
    }

    public CtrlLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public CtrlLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setScrollEnabled(boolean flag) {
        this.isScrollEnabled = flag;
    }

    @Override
    public boolean canScrollVertically() {
        //Similarly you can customize "canScrollHorizontally()" for managing horizontal scroll
        return isScrollEnabled && super.canScrollVertically();
    }
}
