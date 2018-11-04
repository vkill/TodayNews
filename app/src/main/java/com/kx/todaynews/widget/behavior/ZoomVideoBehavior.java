package com.kx.todaynews.widget.behavior;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.kx.todaynews.R;
import com.kx.todaynews.constants.Constant;

import java.lang.ref.WeakReference;

/**
 * Created by admin on 2018/11/1.
 */
public class ZoomVideoBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    private WeakReference<ZoomVideoHeader> dependentView;


    public ZoomVideoBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, RecyclerView child, int layoutDirection) {
        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (lp.height == CoordinatorLayout.LayoutParams.MATCH_PARENT) {
            child.layout(0, 0, parent.getWidth(), (int) (parent.getHeight() - getImageViewHeight()));
            return true;
        }
        return super.onLayoutChild(parent, child, layoutDirection);
    }
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        if (dependency != null && dependency.getId() == R.id.zoomVideoHeader) {
            dependentView = new WeakReference<>((ZoomVideoHeader)dependency);
            return true;
        }
        return false;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {

        float progress =(Math.abs(dependency.getY()) / getDependentViewCollapsedHeight());

      //  System.out.println(progress);
        child.setTranslationY(dependency.getHeight()+dependency.getY());
        child.setAlpha(progress);

        float scale = Constant.VIDEO_SCALE_X + (1-Constant.VIDEO_SCALE_X) * progress;
        getDependentView().setScaleX(scale);
      //  System.out.println(dependency.getTop());

        return true;
    }
    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, RecyclerView child, View directTargetChild, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {

        if (dy<0){

        }
       // System.out.println("consumed=  "+consumed[1]);
//        if (getDependentView().getY() <=0){
//            return;
//        }
       // System.out.println(dy);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull RecyclerView child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type) {
        //  recyclerView  滑动到顶部 且向下滑
        if (dyUnconsumed <0 ){

            getDependentView().MoveDown();

        }
    }

    private ZoomVideoHeader getDependentView() {
        return dependentView.get();
    }
    private float getDependentViewCollapsedHeight() {
        return getDependentView().getResources().getDimension(R.dimen.dp150);
    }
    private float getImageViewHeight() {
        return getDependentView().getResources().getDimension(R.dimen.dp200);
    }
}
