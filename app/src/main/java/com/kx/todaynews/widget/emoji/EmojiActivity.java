package com.kx.todaynews.widget.emoji;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kx.todaynews.R;
import com.kx.todaynews.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmojiActivity extends Activity {

    @BindView(R.id.iv_emoji)
    CheckBox emojiView;
    @BindView(R.id.panel_root)
    LinearLayout mPanelRoot;
    @BindView(R.id.send_edt)
    EditText sendEdt;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_emoji);
        ButterKnife.bind(this);
        mInputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        getWindow().setAttributes(lp);
       // customGlobalLayoutListener = new CustomGlobalLayoutListener(this, scrollView, mBtnNextStep);
       // scrollView.getViewTreeObserver().addOnGlobalLayoutListener(customGlobalLayoutListener);

        showSoftInput();
        emojiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPanelRoot.isShown()) {
                    // lockContentHeight();
                    // hideEmotionLayout(true);
                    emojiView.setChecked(false);
                    mPanelRoot.setVisibility(View.INVISIBLE);
                    showSoftInput();
                    // unlockContentHeightDelayed();
                } else {
                    hideSoftInput();
                    mPanelRoot.setVisibility(View.VISIBLE);
//                    if (isSoftInputShown()) {
//                       // lockContentHeight();
//                        showEmotionLayout();
//                        emojiView.setChecked(true);
//                        //unlockContentHeightDelayed();
//                    } else {
//                        showEmotionLayout();
//                    }
                }
            }
        });
    }
    private InputMethodManager mInputManager;
    private void showSoftInput() {
        sendEdt.requestFocus();
        sendEdt.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(sendEdt, 0);
            }
        });
    }

    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(sendEdt.getWindowToken(), 0);
    }

    public void xx(View view) {
       // @android:color/transparent
        ToastUtils.showToast("点击了白色区域");
    }
}
