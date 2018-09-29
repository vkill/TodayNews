package com.kx.todaynews.widget.emoji;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
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
import com.kx.todaynews.widget.SoftKeyBoardListener;


public class CommentDialog extends DialogFragment implements TextWatcher, View.OnClickListener {

    private TextView sendView;
    private String hintText;
     private Dialog dialog;
    private EditText inputText;
    private CheckBox emojiView;
    private ImageView iv_type;
    private LinearLayout ll_commit,ll_emoji,ll_root;
    private LinearLayout mPanelRoot ;
    //点击发表，内容不为空时的回调
    public SendListener mSendListener;
    private  int mSoftKeyBoardHeight = 0;
    private  int tempSoftInputHeight;
    private InputMethodManager mInputManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        mInputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SoftKeyBoardListener.setListener(getActivity(), new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                tempSoftInputHeight = height;
                mSoftKeyBoardHeight = height;
            }

            @Override
            public void keyBoardHide(int height) {
                //  LogUtils.e("keyBoardHide = "  + height);
                //if (commentDialog!=null)
                // commentDialog.setSoftKeyBoardHeight(0);
            }
        });
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View contentView = initDialog();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        setLayoutParams();
        initView(contentView);
        return dialog;
    }
    public void setSoftKeyBoardHeight(int softKeyBoardHeight) {
        tempSoftInputHeight = softKeyBoardHeight;
        mSoftKeyBoardHeight = softKeyBoardHeight;
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
        sendView.setOnClickListener(this);
        emojiView.setOnClickListener(this);
        iv_type.setOnClickListener(this);
        inputText.setFocusable(true);
        inputText.setFocusableInTouchMode(true);
        inputText.requestFocus();

        ViewPager viewPager = contentView.findViewById(R.id.viewPager);
        CircleIndicator circleIndicator = contentView.findViewById(R.id.circleIndicator);
        EmoJiHelper emojiHelper = new EmoJiHelper(0, getContext(), inputText);
        EmoJiContainerAdapter mAdapter = new EmoJiContainerAdapter(emojiHelper.getPagers());
        viewPager.setAdapter(mAdapter);
        circleIndicator.setViewPager(viewPager);

        showSoftInput();
        inputText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mPanelRoot.isShown()) {
                    if (!isSoftInputShown()) {
                        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
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
    }

    @NonNull
    private View initDialog() {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        dialog = new Dialog(getActivity(), R.style.Comment_Dialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        View dialogContentView = View.inflate(getActivity(), R.layout.dialog_comment, null);
        contentView = dialogContentView.findViewById(R.id.contentView);
        dialog.setContentView(dialogContentView);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialogContentView;
    }
    View contentView ;
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


                break;
            case R.id.iv_emoji:
                if (!isSoftInputShown()) {
                    dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                    emojiView.setChecked(false);
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout();//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {
                        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        lockContentHeight();
                        showEmotionLayout();
                        emojiView.setChecked(true);
                        unlockContentHeightDelayed();
                    }
                }

                break;

        }
    }
    private void showEmotionLayout() {
        hideSoftInput();
        tempSoftInputHeight = 0;
        if (!mPanelRoot.isShown()) {
            mPanelRoot.getLayoutParams().height = mSoftKeyBoardHeight;
            mPanelRoot.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 隐藏表情布局
     * @param
     */
    private void hideEmotionLayout() {
        if (mPanelRoot.isShown()) {
              showSoftInput();
            // mPanelRoot.setVisibility(View.INVISIBLE);
            tempSoftInputHeight = mSoftKeyBoardHeight;
        }
    }
    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) contentView.getLayoutParams();
        params.height = contentView.getHeight();
        params.weight = 0.0F;
    }
    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        inputText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) contentView.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }
    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
       // inputText.requestFocus();
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
        return tempSoftInputHeight != 0;
    }

    private void checkContent() {
        String content = inputText.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(getContext(), "评论内容不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mSendListener != null) {
            mSendListener.sendComment(content);
        }
       // dismiss();
    }

    public interface SendListener {
        void sendComment(String inputText);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mSendListener != null) {
            mSendListener = null;
        }
    }

}
