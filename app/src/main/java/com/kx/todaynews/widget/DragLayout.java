package com.kx.todaynews.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import com.kx.todaynews.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by admin on 2018/11/8.
 */
public class DragLayout extends FrameLayout {
    private ArgbEvaluator argbEvaluator;
    private View dragView;
    private Toolbar mToolbar;
    private ViewPager mContentView;
    private ViewDragHelper mViewDragHelper;
    /**
     *  判断当手指第一次按下所属区域：DragView  or  ContentView
     *
     *   当为true时, Action_down 和 Action_Move  事件下,ContentView不做任何处理
     *   当为false时,在Action_down 中移动ContentView 时根据 progress 改变 DragView的透明值，
     */
    private boolean isDraged= true;
    // STATUS_EXPANDED状态下Top值
    private int mExpandedTop;
    // STATUS_COLLAPSED状态下Top值
    private int mCollapsedTop;
    // 展开 最大高度为DragLayout高度的 1/2 ,
    static final int STATUS_EXPANDED = 1001;
    // 收缩 高度为 FixHeight
    static final int STATUS_COLLAPSED = 1002;

    static final int STATUS_HIDE = 1003;
    /**
     *  拖拽状态
     *  ViewPager 切换页面后会回调 DragLayout.onLayout()方法，
     *  根据这个状态来控制DragView的Layout位置
     */
    @DragStatus
    private int mDragStatus = STATUS_COLLAPSED;
    /**
     *  ViewPager切换后,执行scrollInScreen动画时，DragView显示的状态和切换之前的状态一样。
     *  执行 scrollInScreen 和  scrollOutScreen 动画前 DragView 显示的状态，要么 STATUS_EXPANDED  要么 STATUS_COLLAPSED 。
     *  根据这个状态来控制DragView的动画。
     */
    int lastDragStatus  = STATUS_COLLAPSED;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_EXPANDED, STATUS_COLLAPSED,STATUS_HIDE})
    @interface DragStatus {
    }
    public DragLayout(@NonNull Context context) {
        this(context,null);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DragLayout, 0, 0);
        mFixHeight = a.getDimensionPixelOffset(R.styleable.DragLayout_fix_height, mFixHeight);
        a.recycle();
        mViewDragHelper = ViewDragHelper.create(this,1f,mCallback );
        argbEvaluator = new ArgbEvaluator();
    }

    ViewDragHelper.Callback mCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return  child== dragView;
        }

        /**
         *  限制移动的上下边界
         */
        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (child== dragView){
                if (top <= mHeight -  child.getHeight()){
                    top =  mHeight -  child.getHeight();
                }else if (top >=  mHeight - mFixHeight ){
                    top = mHeight - mFixHeight;
                }
            }
            return top;
        }
        /**
         *  松手时回调
         */
        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                int controlY = mHeight - releasedChild.getHeight() / 2 ;
                int top = releasedChild.getTop();
                if (lastDragStatus == STATUS_EXPANDED && Math.abs(yvel) <= 5000 && yvel>0){

                    ValueAnimator valueAnimator = ValueAnimator.ofInt((int) releasedChild.getY(),mCollapsedTop);
                    valueAnimator.setInterpolator(new BounceInterpolator());
                    valueAnimator.setDuration(1000);
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            int value = (int) animation.getAnimatedValue();
                            dragView.layout( dragView.getLeft(), value,dragView.getRight(),value+dragView.getHeight()  );
                        }
                    });
                    valueAnimator.start();
                    mDragStatus = STATUS_COLLAPSED;
                    mDragViewTop = mHeight - mFixHeight;
                    lastDragStatus  = STATUS_COLLAPSED;
                }else {
                    // 向上位移
                    if (top < controlY){
                        mViewDragHelper.smoothSlideViewTo(releasedChild,0,mHeight - releasedChild.getHeight());
                        mDragStatus = STATUS_EXPANDED;
                        mDragViewTop = mHeight - releasedChild.getHeight();
                        lastDragStatus  = STATUS_EXPANDED;
                    }else if (top > controlY){
                        // 向下位移
                        mViewDragHelper.smoothSlideViewTo(releasedChild,0,mHeight -mFixHeight);
                        mDragStatus = STATUS_COLLAPSED;
                        mDragViewTop = mHeight - mFixHeight;
                        lastDragStatus  = STATUS_COLLAPSED;
                    }
                    ViewCompat.postInvalidateOnAnimation(DragLayout.this);
                }
        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(DragLayout.this);
        }
        super.computeScroll();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        mViewDragHelper.processTouchEvent(event);
        int action = event.getAction();
        float progress =0;

        switch (action){
            case MotionEvent.ACTION_DOWN:
                downY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY();
                if (!isDraged ){
                    //  根据移动的距离移动mContentView
                    mContentView.setTranslationY(moveY - downY);
                    progress = Math.abs((moveY - downY) * 2 / mContentView.getHeight() );
                    if (progress>1){
                        progress=1;
                    }
                    // 根据 progress 改变dragView和mToolbar的透明度
                    if (progress <= 0.5){
                        dragView.setAlpha(1-progress *2);
                        mToolbar.setAlpha(1-progress *2);
                    }
                    // 根据 progress 改变背景的透明度
                    this.setBackgroundColor((int) argbEvaluator.evaluate(
                            progress,
                            getResources().getColor(android.R.color.black),
                            getResources().getColor(android.R.color.transparent)));
                }
                break;
            case MotionEvent.ACTION_UP:
                if (isDraged ){
                    return true ;
                }
                float upY = event.getY();
                 progress = (upY- downY) * 2 / mContentView.getHeight();
                if (Math.abs(progress)>1){
                    progress=1;
                }
                ObjectAnimator objectAnimator;
                //  Math.abs(progress) <0.5时，  mContentView回复原来的位置状态。
                if (Math.abs(progress) <=0.5 ){
                     objectAnimator = ObjectAnimator.ofFloat(mContentView, "translationY", upY - downY, 0);
                     isDraged = true;
                }else {
                  //  Math.abs(progress) > 0.5  %%  progress>0 时  向下移出屏幕，关闭Activity
                    if (progress>0){
                        objectAnimator = ObjectAnimator.ofFloat(mContentView, "translationY", upY - downY, mContentView.getHeight()  );
                    }else {
                        //  Math.abs(progress) > 0.5  %%  progress < 0 时  向上移出屏幕，关闭Activity
                        objectAnimator = ObjectAnimator.ofFloat(mContentView, "translationY", upY - downY,- mContentView.getHeight()  );
                    }
                    objectAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (mOnSlideExitScrollListener!=null){
                                mOnSlideExitScrollListener.onSlideExit();
                            }
                        }
                    });
                }
                objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        float progress = Math.abs((value) * 2 / mContentView.getHeight() );
                        if (progress>1){
                            progress=1;
                        }
                        if (progress <= 0.5){
                            dragView.setAlpha(1-progress *2);
                            mToolbar.setAlpha(1-progress *2);
                        }
                        DragLayout.this.setBackgroundColor((int) argbEvaluator.evaluate(
                                progress,
                                getResources().getColor(android.R.color.black),
                                getResources().getColor(android.R.color.transparent)));

                    }
                });

                objectAnimator.setDuration(500);
                objectAnimator.start();
                break;
        }
        return true;
    }
    float downX =0;
    float downY =0;
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 调用父类的方法，避免可能出现的 IllegalArgumentException: pointerIndex out of range
        super.onInterceptTouchEvent(ev);

        int action = ev.getAction();

        switch (action){
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                if (mViewDragHelper.isViewUnder(dragView, (int) ev.getX(), (int) ev.getY())){
                    isDraged = true;
                }else {
                    isDraged = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                float moveY = ev.getY();
                // 拦截竖直方向上的滑动
                if (Math.abs(moveY - downY)>Math.abs(moveX - downX)){

                    return true ;
                }
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        if (mViewDragHelper.isViewUnder(dragView, (int) ev.getX(), (int) ev.getY())){
            return true ;
        }
        return super.onInterceptTouchEvent(ev);
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mMaxHeight == 0) {
            // 未设置最大高度则为布局高度的 1/2
            mMaxHeight = getMeasuredHeight() / 2;
        } else if (mMaxHeight > getMeasuredHeight()) {
            // 最大高度不超过布局高度
            mMaxHeight = getMeasuredHeight() / 2;
        }
        View childView = getChildAt(1);
        MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
        int childWidth = childView.getMeasuredWidth();
        int childHeight = childView.getMeasuredHeight();
        // 限定视图的最大高度
        if (childHeight > mMaxHeight) {
            childView.measure(MeasureSpec.makeMeasureSpec(childWidth - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mMaxHeight - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY));
        }
        // 限定视图的最小高度
        if (childHeight<mFixHeight){
            childView.measure(MeasureSpec.makeMeasureSpec(childWidth - lp.leftMargin - lp.rightMargin, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(mFixHeight - lp.topMargin - lp.bottomMargin, MeasureSpec.EXACTLY));
        }
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        View childView = getChildAt(1);
        MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
        int childWidth = childView.getMeasuredWidth();
        int childHeight = childView.getMeasuredHeight();

        if (mFixHeight > childHeight){
         // mFixHeight = childHeight;
        }
        mExpandedTop = bottom - childHeight;
        mCollapsedTop = bottom - mFixHeight;
        if (mDragStatus == STATUS_COLLAPSED) {
            mDragViewTop = bottom - mFixHeight;
        } else if (mDragStatus == STATUS_EXPANDED) {
            mDragViewTop = bottom - childHeight;
        } else if (mDragStatus == STATUS_HIDE) {
            mDragViewTop = bottom ;
        }
        childView.layout(lp.leftMargin, mDragViewTop, lp.leftMargin + childWidth, mDragViewTop + childHeight);

    }

    /**
     * DragView 滚出屏幕
     *
     * @param duration 时间
     */
    public void scrollOutScreen(int duration) {
        ValueAnimator valueAnimator ;
        if (lastDragStatus == STATUS_EXPANDED ){
            valueAnimator = ValueAnimator.ofInt(mExpandedTop,mHeight);
        }else  {
            valueAnimator = ValueAnimator.ofInt(mCollapsedTop,mHeight);
        }
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                dragView.layout( dragView.getLeft(), value,dragView.getRight(),value+dragView.getHeight()  );
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                switchStatus();
            }
        });
        valueAnimator.start();
    }

    /**
     * DragView 滚进屏幕
     *
     * @param duration 时间
     */
    public void scrollInScreen(int duration) {
        ValueAnimator valueAnimator ;
        if (lastDragStatus == STATUS_EXPANDED ){
            valueAnimator = ValueAnimator.ofInt(mHeight,mExpandedTop);
        }else  {
             valueAnimator = ValueAnimator.ofInt(mHeight,mCollapsedTop);
        }
        valueAnimator.setDuration(duration);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                dragView.layout( dragView.getLeft(), value,dragView.getRight(),value+dragView.getHeight()  );
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                switchStatus();
            }
        });
        valueAnimator.start();
    }
    /**
     * 切换状态
     */
    private void switchStatus() {
        if (dragView.getY() == mExpandedTop) {
            mDragStatus = STATUS_EXPANDED;
        } else if (dragView.getY() == mCollapsedTop) {
            mDragStatus = STATUS_COLLAPSED;
        } else if (dragView.getY() == mHeight) {
            mDragStatus = STATUS_HIDE;
        }
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        dragView = getChildAt(1);
        mContentView = (ViewPager) getChildAt(0);
        mToolbar = (Toolbar) getChildAt(2);
    }

    // 整个布局高度
    private int mHeight;
    // 文本缩放时的固定高度
    private int mFixHeight ;
    // 文本完全显示的最大高度
    private int mMaxHeight;
    // DragView的Top属性值
    private int mDragViewTop = 0;

    /**
     *  mContentView  向上滑动或向下滑动 关闭Activity的Listener
     */
    public interface onSlideExitScrollListener{
         void onSlideExit( );
     }

    onSlideExitScrollListener mOnSlideExitScrollListener;

    public void setOnSlideExitScrollListener(onSlideExitScrollListener onSlideExitScrollListener) {
        mOnSlideExitScrollListener = onSlideExitScrollListener;
    }
}
