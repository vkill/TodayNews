package com.kx.todaynews.widget.behavior;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;

import com.kx.todaynews.R;
import com.kx.todaynews.constants.Constant;

import cn.jzvd.JzvdStd;


/**
 * Created by admin on 2018/11/1.
 */
public class ZoomVideoHeader extends LinearLayout{
    private float mTouchSlop;
    private int firstDownY ;

    private JzvdStd zoomImage;
    private ViewDragHelper mViewDragHelper;
    private int mMaxScrollHeight;
    private int ANIM_TIME = 350;
    // 是否位于顶部
    private boolean isOnTop = false;
    private CtrlLinearLayoutManager mCtrlLinearLayoutManager;


    public ZoomVideoHeader(Context context) {
        this(context,null);
    }

    public ZoomVideoHeader(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ZoomVideoHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mViewDragHelper = ViewDragHelper.create(this, 1f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return zoomImage == child ;
            }

            /**
             *  控制竖直方向最大的移动距离
             */
            @Override
            public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
                if (top <0){
                    top = 0 ;
                }
                if (mMaxScrollHeight==0){
                    mMaxScrollHeight =getHeight() - zoomImage.getHeight();
                }
                if (top > mMaxScrollHeight){
                    top = mMaxScrollHeight;
                }
                return top;
            }

            @Override
            public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
                System.out.println(top);
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                int  top= releasedChild.getTop();
                if (top <= mMaxScrollHeight /2 ){
                    mViewDragHelper.smoothSlideViewTo(releasedChild,0,0);
                }else {
                    mViewDragHelper.smoothSlideViewTo(releasedChild,0,mMaxScrollHeight);
                }
                postInvalidate();
            }

        });
    }

    @Override
    public void computeScroll() {
        //固定写法
        if (mViewDragHelper.continueSettling(true)) {
            postInvalidate();//注意此处.
        }
    }



    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mMaxScrollHeight==0){
            mMaxScrollHeight =getHeight() - zoomImage.getHeight();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
            // 通过mDragHelper.processTouchEvent(event)来处理事件
    //        mViewDragHelper.processTouchEvent(event);
    //        return true; // 返回 true，表示事件被处理了。
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
              //  System.out.println("currentY = "  + getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                float moveY = event.getY() - firstDownY;
                float currentY = getY();

                //向上滑动viewpager整体移动
                if (currentY + moveY <= 0 && currentY + moveY >= - mMaxScrollHeight) {
                    //doPagerUp(moveY, currentY);
                    setTranslationY(currentY + moveY);
                }else if ( currentY + moveY < -mMaxScrollHeight ){
                    setTranslationY(-mMaxScrollHeight);
                }else if ( currentY + moveY > 0 ){
                    setTranslationY(0);
                }

                break;
            case MotionEvent.ACTION_UP:

                float progress = Math.abs(getTranslationY()) / mMaxScrollHeight;
                // 向上位移
                if ( progress > 0.5  ){
                    MoveUp();
                }else {
                    //  向下位移
                    MoveDown();
                }
                break;
        }
        return super.onTouchEvent(event);
    }
    public void MoveUp(){
        //  向上位移
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this,"translationY",getTranslationY(),-mMaxScrollHeight);
        objectAnimator.setDuration(ANIM_TIME);
        objectAnimator.start();
        if (mCtrlLinearLayoutManager!=null){
            mCtrlLinearLayoutManager.setScrollEnabled(true);
        }
        isOnTop = true;
    }
    public void MoveDown(){
        //  向下位移
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this,"translationY",getTranslationY(),0);
        objectAnimator.setDuration(ANIM_TIME);
        objectAnimator.start();
        if (mCtrlLinearLayoutManager!=null){
            mCtrlLinearLayoutManager.setScrollEnabled(false);
        }
        isOnTop = false;
    }
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
     //  return mViewDragHelper.shouldInterceptTouchEvent(ev);
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                firstDownY = (int) ev.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                if (Math.abs(moveY - firstDownY) > mTouchSlop) {

                    return true;
                }
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        zoomImage = findViewById(R.id.videoplayer);
        setScaleX(Constant.VIDEO_SCALE_X);
       // zoomImage.setScaleY(0.8f);
    }

    public void  setRecyclerView(RecyclerView recyclerView){
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof  CtrlLinearLayoutManager){
            mCtrlLinearLayoutManager = (CtrlLinearLayoutManager) layoutManager;
        }
    }

    public boolean isOnTop() {
        return isOnTop;
    }
}
