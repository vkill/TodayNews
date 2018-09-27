package com.kx.todaynews.widget.emoji;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ActionMode;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.kx.todaynews.R;
import com.kx.todaynews.widget.SoftKeyBoardListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmojiActivity extends AppCompatActivity {

    @BindView(R.id.iv_emoji)
    CheckBox emojiView;
    @BindView(R.id.panel_root)
    LinearLayout mPanelRoot;
    @BindView(R.id.send_edt)
    EditText sendEdt;
    @BindView(R.id.contentView)
    View mContentView;

    int softInputHeight;
    int tempSoftInputHeight;
    InputMethodManager mInputManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setContentView(R.layout.activity_emoji);
        ButterKnife.bind(this);
        mInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setGravity(Gravity.BOTTOM);
       //   getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        getWindow().setAttributes(lp);
        SoftKeyBoardListener.setListener(this, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                tempSoftInputHeight = height;
                softInputHeight = height;
            }

            @Override
            public void keyBoardHide(int height) {
                if (mPanelRoot.isShown() && !emojiView.isChecked()) {
                   // mPanelRoot.setVisibility(View.GONE);
                }
            }
        });
        sendEdt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mPanelRoot.isShown()) {
                    if (!isSoftInputShown()) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                        emojiView.setChecked(false);
                        lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                        hideEmotionLayout();//隐藏表情布局，显示软件盘
                        unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                    }
                    return true;
                }
                return false;
            }
        });
        showSoftInput();
        emojiView.setLongClickable(false);
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            // call that method
            emojiView.setCustomInsertionActionModeCallback( new ActionModeCallbackInterceptor());
        }
        emojiView.setCustomSelectionActionModeCallback(new ActionModeCallbackInterceptor());
        emojiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isSoftInputShown()) {
                     getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                    emojiView.setChecked(false);
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout();//隐藏表情布局，显示软件盘
                    if (mPanelRoot.getVisibility()!=View.GONE){

                    }else {
                    }
                        unlockContentHeightDelayed();

                } else {
                    if (isSoftInputShown()) {
                         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        lockContentHeight();
                        showEmotionLayout();
                        emojiView.setChecked(true);
                        unlockContentHeightDelayed();
                    }
//                    else {
//                        showEmotionLayout();
//                    }
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
        tempSoftInputHeight = 0;
      //  if (!mPanelRoot.isShown()) {
            mPanelRoot.getLayoutParams().height = softInputHeight;
            mPanelRoot.setVisibility(View.VISIBLE);
      //  }
    }

    /**
     * 隐藏表情布局
     *
     * @param
     */
    private void hideEmotionLayout() {
        if (mPanelRoot.isShown()) {
            showSoftInput();
            tempSoftInputHeight = softInputHeight;
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
        mContentView.setLayoutParams(params);
    }

    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        sendEdt.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
                params.weight = 1.0F;
                mContentView.setLayoutParams(params);
            }
        }, 0);
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
     *
     * @return
     */
    private boolean isSoftInputShown() {
        return tempSoftInputHeight != 0;
    }

    public void xx(View view) {
        finish();
        overridePendingTransition(0, R.anim.activity_fade_out);
    }

   class ActionModeCallbackInterceptor implements AbsListView.MultiChoiceModeListener {

       @Override
       public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

       }

       @Override
       public boolean onCreateActionMode(ActionMode mode, Menu menu) {
           return false;
       }

       @Override
       public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
           return false;
       }

       @Override
       public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
           return false;
       }

       @Override
       public void onDestroyActionMode(ActionMode mode) {

       }
   }
}
