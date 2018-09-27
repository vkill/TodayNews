package com.kx.todaynews.widget.emoji;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kx.todaynews.R;
import com.kx.todaynews.utils.LogUtils;
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.widget.SoftKeyBoardListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmojiActivity extends Activity {

    @BindView(R.id.iv_emoji)
    CheckBox emojiView;
    @BindView(R.id.panel_root)
    LinearLayout mPanelRoot;
    @BindView(R.id.send_edt)
    EditText sendEdt;
    @BindView(R.id.contentView)
    View mContentView;
    private CustomGlobalLayoutListener customGlobalLayoutListener;


    @Override
    protected void onDestroy() {//防止内存溢出
        super.onDestroy();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(customGlobalLayoutListener);
//        } else {
//            scrollView.getViewTreeObserver().removeGlobalOnLayoutListener(customGlobalLayoutListener);
//        }
    }
    int softInputHeight ;
    InputMethodManager  mInputManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_emoji);
        ButterKnife.bind(this);
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        //  getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        getWindow().setAttributes(lp);
        // customGlobalLayoutListener = new CustomGlobalLayoutListener(this, scrollView, mBtnNextStep);
        // scrollView.getViewTreeObserver().addOnGlobalLayoutListener(customGlobalLayoutListener);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                softInputHeight = height;
                System.out.println("keyBoardShow = " + height);
            }

            @Override
            public void keyBoardHide(int height) {
                softInputHeight = 0 ;
                System.out.println("keyBoardHide = " + height);
            }
        });
        sendEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_UP){
                    if (!isSoftInputShown()){
                        lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                        hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                        unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                    }
                    return true;
                }
                return false;
            }
        });
        showSoftInput();
        emojiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSoftInputShown()) {
                    emojiView.setChecked(false);
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {
                    if (isSoftInputShown()) {
                        lockContentHeight();
                        showEmotionLayout();
                        emojiView.setChecked(true);
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();
                    }
                }
            }
        });
    }
    private void showEmotionLayout() {
//        int softInputHeight = getSupportSoftInputHeight();
//        if (softInputHeight == 0) {
//           // softInputHeight = sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 400);
//        }
        hideSoftInput();
        mPanelRoot.getLayoutParams().height = softInputHeight;
        mPanelRoot.setVisibility(View.VISIBLE);
    }
    /**
     * 隐藏表情布局
     * @param showSoftInput 是否显示软件盘
     */
    private void hideEmotionLayout(boolean showSoftInput) {
        if (mPanelRoot.isShown()) {
            if (showSoftInput) {
                showSoftInput();
            }
           // mPanelRoot.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.height = mContentView.getHeight();
        params.weight = 0.0F;
    }
    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        sendEdt.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mContentView.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }
    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        sendEdt.requestFocus();
        sendEdt.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(sendEdt, 0);
            }
        });
    }
    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(sendEdt.getWindowToken(), 0);
    }
    /**
     * 是否显示软件盘
     * @return
     */
    private boolean isSoftInputShown() {
        return softInputHeight != 0;
    }

    public void xx(View view) {
        // @android:color/transparent
       // ToastUtils.showToast("点击了白色区域");
        finish();
        overridePendingTransition(0, R.anim.activity_fade_out);
    }
}
