package com.kx.todaynews.widget.loadinglayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.kx.todaynews.R;
import com.kx.todaynews.utils.LogUtils;

/**
 * 高仿今日头条loading flash效果
 */
public class LoadingFlashView extends View implements View.OnFocusChangeListener{

    private static final int DEFAULT_MASK_WIDTH = 160;
    private static final int DEFAULT_MASK_HEIGHT = 80;
    private static final int DEFAULT_DURATION = 1200;

    private Paint mDstPaint;
    private Paint mSrcPaint;
    private Bitmap mSrcBitmap;
    private Bitmap mMaskBitmap;
    private Bitmap mRenderMaskBitmap;
    private Canvas mRenderMaskBitmapCanvas;
    private int mMaskWidth;
    private int mMaskHeight;
    private float mHorMoveDistance;
    private int[] mStartColor = {0, -1, 0};
    private float[] mEndColor = {0.4F, 0.6F, 0.8F};
    private int mStartAlpha = 130;
    private ObjectAnimator mAnimator;
    private boolean mPlaying = false;
    private int mFlashDuration;
    private float percent ;
    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
                stop();
        }
    };

    public LoadingFlashView(@NonNull Context context) {
        this(context, null);
    }

    public LoadingFlashView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingFlashView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFlashDuration = DEFAULT_DURATION;
        mSrcPaint = new Paint();
        mSrcPaint.setAlpha(mStartAlpha);
        Xfermode mode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        mDstPaint = new Paint();
        mDstPaint.setAntiAlias(true);
        mDstPaint.setDither(true);
        mDstPaint.setFilterBitmap(true);
        mDstPaint.setXfermode(mode);
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        mMaskWidth = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_MASK_WIDTH, metrics));
        mMaskHeight = ((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, DEFAULT_MASK_HEIGHT, metrics));
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flash);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int resultW = widthSize;
        int resultH = heightSize;
        if (widthMode == MeasureSpec.AT_MOST) {
            resultW = mSrcBitmap.getWidth();
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            resultH = mSrcBitmap.getHeight();
        }
        setMeasuredDimension(resultW, resultH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ensureSrcBitmapNotNull();
        // 绘制源图
        drawSrcOnCanvas(canvas, mSrcPaint);
        // 在遮罩图上绘制源图
        ensureRenderMaskBitmapNotNull();
        drawSrcOnCanvas(mRenderMaskBitmapCanvas, null);
        // 在遮罩图上绘制渐变遮罩
        ensureMaskBitmapNotNull();
        mRenderMaskBitmapCanvas.drawBitmap(mMaskBitmap, mHorMoveDistance, 0, mDstPaint);
        // 最终映射到控件上
        canvas.drawBitmap(mRenderMaskBitmap, 0, 0, null);
    }

    private void drawSrcOnCanvas(Canvas canvas, Paint paint) {
        canvas.drawBitmap(mSrcBitmap, 0f, 0f, paint);
    }

    private void clearCanvas(Canvas canvas) {
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
    }

    private void setPercent(float percent) {
        System.out.println("LoadingFlashView  刷新中...   " + percent );
        this.percent = percent;
        mHorMoveDistance = this.mMaskWidth * (percent - 1.5f);
        invalidate();
    }

    public float getPercent() {
        return percent;
    }

    public void setImage(int resId) {
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.flash);
    }

    private void ensureSrcBitmapNotNull() {
        if (mSrcBitmap == null) {
            throw new RuntimeException("You need call setImage(int resId) to set a src image!");
        }
    }

    private void ensureRenderMaskBitmapNotNull() {
        if (mRenderMaskBitmap == null) {
            mRenderMaskBitmap = Bitmap.createBitmap(mMaskWidth, mMaskHeight, Bitmap.Config.ARGB_8888);
            mRenderMaskBitmapCanvas = new Canvas(mRenderMaskBitmap);
        }
    }

    private void ensureMaskBitmapNotNull() {
        if (mMaskBitmap == null) {
            int horMoveArea = ((int) (this.mMaskWidth * 2.5f));
            int verMoveArea = this.mMaskHeight;
            Shader shader = new LinearGradient(0.35f * horMoveArea, 1.0f * verMoveArea, 0.65f * horMoveArea, 0.0f * verMoveArea, this.mStartColor, this.mEndColor, Shader.TileMode.CLAMP);
            mMaskBitmap = Bitmap.createBitmap(horMoveArea, verMoveArea, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(mMaskBitmap);
            Paint paint = new Paint();
            paint.setShader(shader);
            canvas.drawRect(0.0f, 0.0f, horMoveArea, verMoveArea, paint);
        }
    }
    public void start() {
        if (mPlaying) {
            return;
        }
        mPlaying = true;
        startAnim();
    }

    public void stop() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
        mPlaying = false;
    }

    private void startAnim() {
        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(this, "percent", 0f, 1.5f);
            mAnimator.setInterpolator(new LinearInterpolator());
            mAnimator.setDuration(mFlashDuration);
            mAnimator.setRepeatCount(-1);
        }
        mAnimator.start();
        mHandler.postDelayed(mRunnable,4 *mFlashDuration );
    }

    public void setDuration(int duration) {
        mFlashDuration = duration;
    }

    public void setSrcAlpha(int startAlpha) {
        this.mStartAlpha = startAlpha;
        if (mSrcPaint != null) {
            mSrcPaint.setAlpha(startAlpha);
        }
    }

    public boolean isPlaying() {
        return mPlaying;
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
    @Override
    public void setVisibility(int visibility) {
        if (visibility == VISIBLE){
            LogUtils.e("setVisibility   VISIBLE");
            start();
        }else {
            LogUtils.e("setVisibility   GONE");
            stop();
        }
        super.setVisibility(visibility);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.e("onAttachedToWindow   ");
        start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.e("onDetachedFromWindow   ");
        stop();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.e("onSizeChanged   ");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        LogUtils.e("onFocusChange   " + (hasFocus));
    }
}
