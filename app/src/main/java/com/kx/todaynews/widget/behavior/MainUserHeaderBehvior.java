package com.kx.todaynews.widget.behavior;

import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kx.todaynews.R;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2018/11/4.
 */
public class MainUserHeaderBehvior extends CoordinatorLayout.Behavior<NestedScrollView> {
    //滑动放大系数，系数越大，滑动时放大程度越大
    private float mScaleRatio =0.35f;
    private WeakReference<RelativeLayout> dependentView;
    private ImageView mImageView ;
    public MainUserHeaderBehvior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
//    @Override
//    public boolean onLayoutChild(CoordinatorLayout parent, NestedScrollView child, int layoutDirection) {
//        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
//            child.layout(0, 0, parent.getWidth(), (int) (parent.getHeight() - getImageViewHeight()));
//            return true;
//        }
//        return super.onLayoutChild(parent, child, layoutDirection);
//    }
//    @Override
//    public boolean onLayoutChild(CoordinatorLayout parent, NestedScrollView child, int layoutDirection) {
//        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
//        if(params!=null && params.height == CoordinatorLayout.LayoutParams.MATCH_PARENT){
//            child.layout(0,0,parent.getWidth(),parent.getHeight());
//            child.setTranslationY(getImageViewHeight());
//            return true;
//        }
//
//        return super.onLayoutChild(parent, child, layoutDirection);
//    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, NestedScrollView child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.rl_bg) {
            dependentView = new WeakReference<>((RelativeLayout)dependency);
            mImageView = getDependentView().findViewById(R.id.iv_bg);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, NestedScrollView child, View dependency) {

        child.setTranslationY(dependency.getHeight());
        float progress =( dependency.getHeight() - getImageViewHeight()) / getImageViewHeight();
        float scale = 1 + progress+ 0.1f ;
        mImageView.setScaleX(scale);
        return true;
    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, NestedScrollView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        if (dy>0){
            return;
        }
        //  只管向下滑， dy<0
        RelativeLayout dependentView = getDependentView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) dependentView.getLayoutParams();
        params.height = (int) (dependentView.getHeight()-dy*mScaleRatio);
        dependentView.setLayoutParams(params);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type);
    }
    @Override
    public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull NestedScrollView child, @NonNull View target, int type) {
        if (getDependentView().getHeight() == getImageViewHeight()){
            return;
        }
            RelativeLayout dependentView = getDependentView();
            ValueAnimator valueAnimator = ValueAnimator.ofFloat(dependentView.getHeight(),getImageViewHeight());
            valueAnimator.setDuration(400);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float value = (float) animation.getAnimatedValue();
                    System.out.println(value);
                    CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) dependentView.getLayoutParams();
                    params.height = (int) value;
                    dependentView.setLayoutParams(params);
                }
            });
            valueAnimator.start();
    }


    private RelativeLayout getDependentView() {
        return dependentView.get();
    }
    private float getImageViewHeight() {
        return getDependentView().getResources().getDimension(R.dimen.dp180);
    }
}
