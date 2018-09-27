package com.kx.todaynews.widget.emoji;

import android.content.Context;
import android.text.method.BaseMovementMethod;
import android.text.method.MovementMethod;
import android.util.AttributeSet;

/**
 * Created by admin on 2018/9/28.
 */
public class NulllEditText extends android.support.v7.widget.AppCompatEditText {
    public NulllEditText(Context context) {
        super(context);
    }

    public NulllEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NulllEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        return new BaseMovementMethod();
    }
}
