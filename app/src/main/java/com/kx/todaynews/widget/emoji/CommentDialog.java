package com.kx.todaynews.widget.emoji;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kx.todaynews.R;
import com.kx.todaynews.widget.keyboard.widget.KPSwitchPanelLinearLayout;


public class CommentDialog extends DialogFragment implements TextWatcher, View.OnClickListener {

    private TextView sendView;
    private String hintText;
   // private Dialog dialog;
    private EditText inputText;
    private CheckBox emojiView;
    private KPSwitchPanelLinearLayout mPanelRoot ;
    private  ListView mChatContent;
    //点击发表，内容不为空时的回调
    public SendListener mSendListener;
    private  int mSoftKeyBoardHeight = 0;
    private InputMethodManager mInputManager;
    private SharedPreferences sp;
    private static final String SHARE_PREFERENCE_NAME = "com.mumu.easyemoji";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mInputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        sp = getActivity().getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = View.inflate(getActivity(), R.layout.dialog_comment, null);
        initView(contentView);
        return contentView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setLayoutParams();
    }

    public void setSoftKeyBoardHeight(int softKeyBoardHeight) {
        if (mSoftKeyBoardHeight ==0){
            mSoftKeyBoardHeight = softKeyBoardHeight;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mPanelRoot.getLayoutParams();
            layoutParams.height = mSoftKeyBoardHeight;
            mPanelRoot.setLayoutParams(layoutParams);
        }
    }
    public CommentDialog() {

    }


    @SuppressLint("ValidFragment")
    public CommentDialog(ListView listView, String hintText, SendListener sendBackListener) {//提示文字
        mChatContent  = listView;
        this.hintText = hintText;
        this.mSendListener = sendBackListener;
    }


    private void initView(View contentView) {
        emojiView = contentView.findViewById(R.id.iv_emoji);
        inputText =contentView.findViewById(R.id.send_edt);
        inputText.setHint(hintText);
        sendView = contentView.findViewById(R.id.dialog_comment_send);
        mPanelRoot = contentView.findViewById(R.id.panel_root);

        inputText.addTextChangedListener(this);
      //  sendView.setOnClickListener(this);
      //  emojiView.setOnClickListener(this);
        inputText.setFocusable(true);
        inputText.setFocusableInTouchMode(true);
        inputText.requestFocus();

        final Handler handler = new Handler();

       // dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

        //    @Override
          //  public void onDismiss(DialogInterface dialog) {

//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        hideSoftKeyBoard(getActivity());
//                    }
//                }, 0);
//
//                KPSwitchConflictUtil.hidePanelAndKeyboard(mPanelRoot);
//            }
//        });
//        KeyboardUtil.attach(getActivity(), mPanelRoot,
//                // Add keyboard showing state callback, do like this when you want to listen in the
//                // keyboard's show/hide change.
//                new KeyboardUtil.OnKeyboardShowingListener() {
//                    @Override
//                    public void onKeyboardShowing(boolean isShowing) {
//
//                    }
//                });
        boolean isMultiSubPanel = false;
        if (isMultiSubPanel) {
            // If there are several sub-panels in this activity ( e.p. function-panel, emoji-panel).
//            KPSwitchConflictUtil.attach(mPanelRoot, inputText,
//                    new KPSwitchConflictUtil.SwitchClickListener() {
//                        @Override
//                        public void onClickSwitch(View v, boolean switchToPanel) {
//                            if (switchToPanel) {
//                                inputText.clearFocus();
//                            } else {
//                                inputText.requestFocus();
//                            }
//                        }
//                    },
                  //  new KPSwitchConflictUtil.SubPanelAndTrigger(mSubPanel1, mPlusIv1),
                  //  new KPSwitchConflictUtil.SubPanelAndTrigger(mSubPanel2, mPlusIv2));
        } else {
           //  In the normal case.
//            KPSwitchConflictUtil.attach(mPanelRoot, emojiView, inputText,
//                    new KPSwitchConflictUtil.SwitchClickListener() {
//                        @Override
//                        public void onClickSwitch(View v, boolean switchToPanel) {
//                            LogUtils.e( String.format("是否显示emoji键盘     %s ", switchToPanel? "是":"否"));
//                            if (switchToPanel) {
//                                inputText.clearFocus();
//                            } else {
//                               inputText.requestFocus();
//                            }
//                        }
//                    });
        }

      //  mDetector = EmotionInputDetector.with(getActivity())
              //  .bindSendButton(sendView)
              //  .bindToEditText(inputText)
              //  .setEmotionView(mllFaceContainer)
              //  .bindToContent(mChatContent)
              //  .bindToEmotionButton(emojiView);
//        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
//            @Override
//            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                   // hideSoftKeyBoard(getActivity());
//                  // dismiss();
//                    return true;
//                }
//                return false;
//            }
//        });

    }

    private void hideSoftKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        mPanelRoot.setVisibility(View.GONE);
    }

//    @NonNull
//    private View initDialog() {
//        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
//        dialog = new Dialog(getActivity(), R.style.Comment_Dialog);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
//        View contentView = View.inflate(getActivity(), R.layout.dialog_comment, null);
//        dialog.setContentView(contentView);
//        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
//        return contentView;
//    }

    private void setLayoutParams() {
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = getActivity().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        System.out.println("getSupportSoftInputHeight() = "  + getSupportSoftInputHeight());
        lp.height =getSupportSoftInputHeight();
        window.setAttributes(lp);
       // window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getDialog().setCancelable(false);


    }

    public void cleanText() {
        inputText.setText("");
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > 0) {
            sendView.setEnabled(true);
            sendView.setTextColor(Color.BLACK);
        } else {
            sendView.setEnabled(false);
            sendView.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_comment_send:
                checkContent();
                break;
            case R.id.iv_emoji:
//                if (mPanelRoot.isShown()) {
//                   // lockContentHeight();
//                    hideEmotionLayout(true);
//                    emojiView.setChecked(false);
//                   // unlockContentHeightDelayed();
//                } else {
//                    if (isSoftInputShown()) {
//                       // lockContentHeight();
//                        showEmotionLayout();
//                        emojiView.setChecked(true);
//                        //unlockContentHeightDelayed();
//                    } else {
//                        showEmotionLayout();
//                    }
//                }

        }
    }
    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = sp.getInt(SHARE_PREFERENCE_TAG, 400);
        }
        hideSoftInput();
       // mPanelRoot.getLayoutParams().height = softInputHeight;
        mPanelRoot.setVisibility(View.VISIBLE);
    }
    private void hideEmotionLayout(boolean showSoftInput) {
        if (mPanelRoot.isShown()) {
            mPanelRoot.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }
    private void showSoftInput() {
        inputText.requestFocus();
        inputText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(inputText, 0);
            }
        });
    }

    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
    }

    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        int screenHeight = getActivity().getWindow().getDecorView().getRootView().getHeight();
        int softInputHeight = screenHeight - r.bottom;
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }
        if (softInputHeight < 0) {
            Log.w("EmotionInputDetector", "Warning: value of softInputHeight is below zero!");
        }
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_TAG, softInputHeight).apply();
        }
        return softInputHeight;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        getActivity().getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    private void checkContent() {
        String content = inputText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mSendListener != null) mSendListener.sendComment(content);
        dismiss();
    }

    public interface SendListener {
        void sendComment(String inputText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSendListener != null) mSendListener = null;
    }

}
