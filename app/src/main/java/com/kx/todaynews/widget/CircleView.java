package com.kx.todaynews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.kx.todaynews.R;

/**
 * Created by admin on 2018/11/14.
 *  小圆点
 */
public class CircleView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private int defaultColor = 0xff3DCF03 ;

    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleView,0,0);

        defaultColor = array.getColor(R.styleable.CircleView_circle_color,defaultColor);

        mPaint.setColor(defaultColor);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int size = 0 ;


        if (widthSize > heightSize){
            size = heightSize;
        }else {
            size = widthSize;
        }
        setMeasuredDimension(size,size);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawCircle(0,0,getWidth()/2,mPaint);
    }
}
