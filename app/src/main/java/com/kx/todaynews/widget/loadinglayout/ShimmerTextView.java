package com.kx.todaynews.widget.loadinglayout;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * 文字闪动效果
 */
public class ShimmerTextView extends android.support.v7.widget.AppCompatTextView {
    private Paint mPaint;
    private int mDx;
    private int width ;
    private int height ;
    Matrix matrix = new Matrix();
    private LinearGradient mLinearGradient;
    private ValueAnimator mAnimator;
    Typeface mTypeFace ;
    private Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mAnimator.cancel();
        }
    };

    public ShimmerTextView(Context context) {
        super(context);
        init();
    }

    public ShimmerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShimmerTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mAnimator = ValueAnimator.ofInt(0,2*width);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                        mDx = (Integer) animation.getAnimatedValue();
                        postInvalidate();
                }
        });
        mAnimator.setRepeatMode(ValueAnimator.RESTART);
        mAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.setDuration(1300);
        //多色渐变
        int[] colors = {0x55000000,0x55000000,0xff0000ff,0x55000000,0x55000000};
        float[]  pos = {0f,0.3f,0.4f,0.5f,1.0f};
        mLinearGradient = new LinearGradient( -width,height,0,0, colors, pos, Shader.TileMode.CLAMP);
        mAnimator.start();
        mHandler.postDelayed(mRunnable,5200);
    }

    private void init(){
        mPaint =getPaint();
        mTypeFace =Typeface.createFromAsset(getContext().getAssets(),"fonts/DINCondensedBold.ttf");
        mPaint.setTypeface(mTypeFace);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAnimator.start();
                mHandler.postDelayed(mRunnable,5200);
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
         matrix.setTranslate(mDx,0);
         mLinearGradient.setLocalMatrix(matrix);
         mPaint.setShader(mLinearGradient);
        //多色渐变
        super.onDraw(canvas);
    }
}