package com.kx.todaynews.widget.fab;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.kx.todaynews.R;
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.utils.UiUtils;


public class FloatingActionsMenu extends ViewGroup {

    private int mChildCount;
    /**
     *  FloatingActionButton 之间的间距.默认5dp
     */
    private int mButtonSpacing  ;

    public FloatingActionsMenu(@NonNull Context context) {
        this(context,null);
    }

    public FloatingActionsMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatingActionsMenu(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FloatingActionsMenu, 0, 0);
        mButtonSpacing = typedArray.getDimensionPixelOffset(R.styleable.FloatingActionsMenu_fab_buttonSpacing, UiUtils.dp2px(context,5));
        typedArray.recycle();
        mTouchDelegateGroup = new TouchDelegateGroup(this);
        setTouchDelegate(mTouchDelegateGroup);

        createAddButton(getContext());

    }
    int totalChildHeight = 0;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int width = 0;
        int height = 0;
       // int maxButtonWidth = 0;
        for (int i = 0; i < mChildCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() == GONE){
                continue;
            }
        //  maxButtonWidth = Math.max(maxButtonWidth,child.getMeasuredWidth());
            width = Math.max(width,child.getMeasuredWidth());
            height += child.getMeasuredHeight();
            totalChildHeight +=  child.getMeasuredHeight();
        }
        height += mButtonSpacing * ( mChildCount - 1);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
       // super.onLayout(changed, l, t, r, b);
        int measuredHeight = getMeasuredHeight();
        int itemLineSpace =  (measuredHeight - totalChildHeight)/ (mChildCount+1);
        int height = itemLineSpace;
          for (int i =0; i <= mChildCount - 1; i++) {
            View child = getChildAt(i);
            int left = child.getLeft();
            int top = height;
            int right = left + child.getMeasuredWidth();
            int bottom = top+child.getMeasuredHeight();
            child.layout(left,top,right,bottom);
            height += (child.getMeasuredHeight()+ itemLineSpace);
            if (child != mAddButton){
                int translationY = (itemLineSpace+ child.getHeight())  * (mChildCount - 1 - i);
                child.setTranslationY(translationY);

                FABLayoutParams layoutParams = (FABLayoutParams) child.getLayoutParams();
                layoutParams.setAnimationsTarget(child,translationY);
                child.setAlpha(0);
            }
        }
    }
    OnFloatingActionButtonClickListener mOnFloatingActionButtonClickListener;

    public void setOnFloatingActionButtonClickListener(OnFloatingActionButtonClickListener listener) {
        mOnFloatingActionButtonClickListener = listener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        bringChildToFront(mAddButton);
        mChildCount = getChildCount();
        for (int i = 0; i < mChildCount; i++) {
            FloatingActionButton child = (FloatingActionButton) getChildAt(i);
            if (child == mAddButton || child.getVisibility()==GONE){
                continue;
            }

            int finalI = i;
            child.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnFloatingActionButtonClickListener!=null){
                            mOnFloatingActionButtonClickListener.OnFloatingActionButtonClick(child, finalI);
                        }
                    }
                });
        }
    }
    private static final long ANIMATION_DURATION = 300;
    private AnimatorSet mExpandAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);
    private AnimatorSet mCollapseAnimation = new AnimatorSet().setDuration(ANIMATION_DURATION);

    private static final float COLLAPSED_PLUS_ROTATION = 0f;
    private static final float EXPANDED_PLUS_ROTATION = 90f + 45f;

    private boolean mExpanded;

    private TouchDelegateGroup mTouchDelegateGroup;

    private AddFloatingActionButton mAddButton;
    private RotatingDrawable mRotatingDrawable;
    private static class RotatingDrawable extends LayerDrawable {
        public RotatingDrawable(Drawable drawable) {
            super(new Drawable[]{drawable});
        }

        private float mRotation;

        @SuppressWarnings("UnusedDeclaration")
        public float getRotation() {
            return mRotation;
        }

        @SuppressWarnings("UnusedDeclaration")
        public void setRotation(float rotation) {
            mRotation = rotation;
            invalidateSelf();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.save();
            canvas.rotate(mRotation, getBounds().centerX(), getBounds().centerY());
            super.draw(canvas);
            canvas.restore();
        }
    }

    private void createAddButton(Context context) {
        mAddButton = new AddFloatingActionButton(context) {
            @Override
            void updateBackground() {
              //  mPlusColor = mAddButtonPlusColor;
             //   mColorNormal = mAddButtonColorNormal;
             //   mColorPressed = mAddButtonColorPressed;
              //  mStrokeVisible = mAddButtonStrokeVisible;
                super.updateBackground();
            }

            @Override
            Drawable getIconDrawable() {
                final RotatingDrawable rotatingDrawable = new RotatingDrawable(super.getIconDrawable());
                mRotatingDrawable = rotatingDrawable;

                final OvershootInterpolator interpolator = new OvershootInterpolator();

                final ObjectAnimator collapseAnimator = ObjectAnimator.ofFloat(rotatingDrawable, "rotation", EXPANDED_PLUS_ROTATION, COLLAPSED_PLUS_ROTATION);
                final ObjectAnimator expandAnimator = ObjectAnimator.ofFloat(rotatingDrawable, "rotation", COLLAPSED_PLUS_ROTATION, EXPANDED_PLUS_ROTATION);

                collapseAnimator.setInterpolator(interpolator);
                expandAnimator.setInterpolator(interpolator);

                mExpandAnimation.play(expandAnimator);
                mCollapseAnimation.play(collapseAnimator);

                return rotatingDrawable;
            }
        };

        mAddButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
        addView(mAddButton, super.generateDefaultLayoutParams());
    }

    private void collapse() {
        if (mExpanded) {
            mExpanded = false;
            mCollapseAnimation.setDuration( ANIMATION_DURATION);
            mCollapseAnimation.start();
            mExpandAnimation.cancel();
            for (int i = 0; i < mChildCount; i++) {
                View child = getChildAt(i);
                if (child == mAddButton || child.getVisibility() == GONE){
                    continue;
                }
                FABLayoutParams layoutParams = (FABLayoutParams) child.getLayoutParams();
                layoutParams.collapse();
            }

        }
    }

    public void toggle() {
        if (mExpanded) {
            collapse();
        } else {
            expand();
        }
    }

    public void expand() {
        if (!mExpanded) {
            mExpanded = true;
           // mTouchDelegateGroup.setEnabled(true);
            mCollapseAnimation.cancel();
            mExpandAnimation.start();
            for (int i = 0; i < mChildCount; i++) {
                View child = getChildAt(i);
                if (child == mAddButton || child.getVisibility() == GONE){
                    continue;
                }
                FABLayoutParams layoutParams = (FABLayoutParams) child.getLayoutParams();
                layoutParams.expand();
            }
        }
    }

    private class FABLayoutParams extends FrameLayout.LayoutParams {

          private  AnimatorSet expandAnimatorSet = new AnimatorSet().setDuration(ANIMATION_DURATION);

          private  AnimatorSet collapseAnimatorSet = new AnimatorSet().setDuration(ANIMATION_DURATION);

          public FABLayoutParams(ViewGroup.LayoutParams source) {
              super(source);

          }
          public void setAnimationsTarget(View child,float translationY) {

              ObjectAnimator mExpandTrans = ObjectAnimator.ofFloat(child,"translationY",translationY,0);
              ObjectAnimator mExpandAlpha = ObjectAnimator.ofFloat(child,"alpha",0,1);
              expandAnimatorSet.playTogether(mExpandTrans,mExpandAlpha);


              ObjectAnimator mCollapseTrans = ObjectAnimator.ofFloat(child,"translationY",0,translationY);
              ObjectAnimator mCollapseAlpha = ObjectAnimator.ofFloat(child,"alpha",1,0);
              collapseAnimatorSet.playTogether(mCollapseTrans,mCollapseAlpha);
          }
          public void expand(){
              expandAnimatorSet.start();
          }
          public void collapse(){
              collapseAnimatorSet.start();
          }
      }

    @Override
    protected FrameLayout.LayoutParams generateDefaultLayoutParams() {
        return new FABLayoutParams(super.generateDefaultLayoutParams());
    }

    @Override
    public FrameLayout.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new FABLayoutParams(super.generateLayoutParams(attrs));
    }

    @Override
    protected FrameLayout.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new FABLayoutParams(super.generateLayoutParams(p));
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return super.checkLayoutParams(p);
    }
}
