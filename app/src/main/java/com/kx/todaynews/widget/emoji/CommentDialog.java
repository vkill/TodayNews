package com.kx.todaynews.widget.emoji;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kx.todaynews.R;
import com.kx.todaynews.widget.keyboard.util.KeyboardUtil;
import com.kx.todaynews.widget.keyboard.widget.KPSwitchPanelLinearLayout;


public class CommentDialog extends DialogFragment implements TextWatcher, View.OnClickListener {

    private TextView sendView;
    private String hintText;
     private Dialog dialog;
    private EditText inputText;
    private CheckBox emojiView;
    private ImageView iv_type;
    private LinearLayout ll_commit,ll_emoji,ll_root;
    private KPSwitchPanelLinearLayout mPanelRoot ;
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
      //  setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mInputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        sp = getActivity().getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View contentView = View.inflate(getActivity(), R.layout.dialog_comment, null);
//        initView(contentView);
//        return contentView;
//    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View contentView = initDialog();
        setLayoutParams();
        initView(contentView);
        return dialog;
    }
    public void setSoftKeyBoardHeight(int softKeyBoardHeight) {
        if (mSoftKeyBoardHeight ==0){
            mSoftKeyBoardHeight = softKeyBoardHeight;
            if (mPanelRoot!=null){
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mPanelRoot.getLayoutParams();
                layoutParams.height = mSoftKeyBoardHeight;
                mPanelRoot.setLayoutParams(layoutParams);
            }
        }
    }
    public CommentDialog() {

    }


    @SuppressLint("ValidFragment")
    public CommentDialog( String hintText, SendListener sendBackListener) {//提示文字
       // mChatContent  = listView;
        this.hintText = hintText;
        this.mSendListener = sendBackListener;
    }


    private void initView(View contentView) {
        emojiView = contentView.findViewById(R.id.iv_emoji);
        iv_type = contentView.findViewById(R.id.iv_type);
        inputText =contentView.findViewById(R.id.send_edt);
        inputText.setHint(hintText);
        sendView = contentView.findViewById(R.id.dialog_comment_send);
        mPanelRoot = contentView.findViewById(R.id.panel_root);
        ll_commit = contentView.findViewById(R.id.ll_commit);
        ll_emoji = contentView.findViewById(R.id.ll_emoji);
        ll_root = contentView.findViewById(R.id.ll_root);

        inputText.addTextChangedListener(this);
      //  sendView.setOnClickListener(this);
        emojiView.setOnClickListener(this);
        iv_type.setOnClickListener(this);
        inputText.setFocusable(true);
        inputText.setFocusableInTouchMode(true);
        inputText.requestFocus();
        inputText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mPanelRoot.isShown()) {
                    if (!isSoftInputShown()){
                        emojiView.setChecked(false);
                        lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                        hideEmotionLayout();//隐藏表情布局，显示软件盘
                       // unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                    }
                }
                return false;
            }
        });
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
        KeyboardUtil.attach(getActivity(), mPanelRoot,
                // Add keyboard showing state callback, do like this when you want to listen in the
                // keyboard's show/hide change.
                new KeyboardUtil.OnKeyboardShowingListener() {
                    @Override
                    public void onKeyboardShowing(boolean isShowing) {
                        System.out.println(isShowing);
                    }
                });
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
        mPanelRoot.setVisibility(View.INVISIBLE);
    }

    @NonNull
    private View initDialog() {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.Comment_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View contentView = View.inflate(getActivity(), R.layout.dialog_comment, null);
        xx = contentView.findViewById(R.id.xx);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // hideSoftKeyBoard(getActivity());
                dialog.dismiss();
            }
        });
        return contentView;
    }
    View xx ;
    private void setLayoutParams() {
        // 设置宽度为屏宽, 靠近屏幕底部。
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
       lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.alpha = 1;
        lp.dimAmount = 0.0f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        window.setAttributes(lp);
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

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
                case R.id.iv_type:
                    String trim = inputText.getText().toString().trim();

                   // SpannableString spannableString = new SpannableString();



                break;
            case R.id.iv_emoji:
                if (mPanelRoot.isShown()) {
                    emojiView.setChecked(false);
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout();//隐藏表情布局，显示软件盘
                  //  unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {
                    lockContentHeight();
                    showEmotionLayout();
                    emojiView.setChecked(true);
                    //unlockContentHeightDelayed();
//                    if (isSoftInputShown()) {
//                    } else {
//                        showEmotionLayout();
//                    }
                }

                break;

        }
    }
    private void showEmotionLayout() {
//        int softInputHeight = getSupportSoftInputHeight();
//        if (softInputHeight == 0) {
//           // softInputHeight = sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 400);
//        }
        hideSoftInput();
        mPanelRoot.getLayoutParams().height = mSoftKeyBoardHeight;
        mPanelRoot.setVisibility(View.VISIBLE);
    }
    /**
     * 隐藏表情布局
     * @param
     */
    private void hideEmotionLayout() {
        if (mPanelRoot.isShown()) {
              showSoftInput();
            // mPanelRoot.setVisibility(View.INVISIBLE);
        }
    }
    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) xx.getLayoutParams();
        params.height = xx.getHeight();
        params.weight = 0.0F;
    }
    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        inputText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) xx.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }
    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        inputText.requestFocus();
        inputText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(inputText, 0);
            }
        });
    }
    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(inputText.getWindowToken(), 0);
    }
    /**
     * 是否显示软件盘
     * @return
     */
    private boolean isSoftInputShown() {
        return mSoftKeyBoardHeight != 0;
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
