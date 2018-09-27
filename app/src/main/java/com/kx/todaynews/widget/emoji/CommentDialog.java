package com.kx.todaynews.widget.emoji;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
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
import com.kx.todaynews.utils.ToastUtils;
import com.kx.todaynews.widget.SoftKeyBoardListener;
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
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
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
       // setLayoutParams();
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
//                WindowManager wm = (WindowManager)getActivity().getSystemService(Context.WINDOW_SERVICE);
//                Point point=new Point();
//                wm.getDefaultDisplay().getSize(point);
//                int width = point.x;
//                int heights = point.y;
//                int result = 0;
//                int resourceId = getActivity().getResources().getIdentifier("status_bar_height", "dimen", "android");
//                if (resourceId > 0) {
//                    result = getActivity().getResources().getDimensionPixelSize(resourceId);
//                }
//                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ll_commit.getLayoutParams();
//                System.out.println("ll_emoji = " + ll_emoji.getHeight());
//                System.out.println("ll_commit = " + ll_commit.getHeight());
//                System.out.println("heights = " + heights);
//                System.out.println("result = " + result);
//                ll_root.layout(0,heights - result -ll_emoji.getHeight()-ll_commit.getHeight(),width,0 );

            }
            @Override
            public void keyBoardHide(int height) {
                ToastUtils.showToast("键盘隐藏 高度" + height);
                System.out.println("键盘隐藏 高度" + height);

            }
        });
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
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mPanelRoot.getLayoutParams();
            layoutParams.height = mSoftKeyBoardHeight;
           // mPanelRoot.setLayoutParams(layoutParams);
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
                    hideEmotionLayout(true);
                    emojiView.setChecked(false);
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
        mPanelRoot.setVisibility(View.GONE);
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
                hideSoftKeyBoard(getActivity());
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



                   // lockContentHeight();
                   // hideEmotionLayout(true);
                    emojiView.setChecked(false);
                    mPanelRoot.setVisibility(View.GONE);
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

                break;

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
            mPanelRoot.setVisibility(View.INVISIBLE);
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
