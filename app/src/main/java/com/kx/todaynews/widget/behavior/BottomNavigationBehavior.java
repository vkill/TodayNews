package com.kx.todaynews.widget.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.CoordinatorLayout.Behavior;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

/**
 * MainActivity底部tab 上滑隐藏 下滑显示
 */
public class BottomNavigationBehavior extends Behavior<View> {

    private ObjectAnimator outAnimator, inAnimator;

    public BottomNavigationBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 垂直滑动
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, View child, View target, int dx, int dy, int[] consumed) {
        // 上滑隐藏
        if (dy > 0) {
            if (outAnimator == null) {
                outAnimator = ObjectAnimator.ofFloat(child, "translationY", 0, child.getHeight());
                outAnimator.setDuration(200);
            }
            if (!outAnimator.isRunning() && child.getTranslationY() <= 0) {
                outAnimator.start();
            }
            // 下滑显示
        } else if (dy < 0) {
            if (inAnimator == null) {
                inAnimator = ObjectAnimator.ofFloat(child, "translationY", child.getHeight(), 0);
                inAnimator.setDuration(200);
            }
            if (!inAnimator.isRunning() && child.getTranslationY() >= child.getHeight()) {
                inAnimator.start();
            }
        }
    }
}