package com.kx.todaynews.module;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kx.todaynews.R;
import com.kx.todaynews.utils.UiUtils;

/**
 * @author Administrator
 */
public class ArticleReplyBottomFragment extends BottomSheetDialogFragment {
    /**
     * The bottom sheet is dragging.
     */
    public static final int STATE_DRAGGING = 1;

    /**
     * The bottom sheet is settling.
     */
    public static final int STATE_SETTLING = 2;

    /**
     * The bottom sheet is expanded.
     */
    public static final int STATE_EXPANDED = 3;

    /**
     * The bottom sheet is collapsed.
     */
    public static final int STATE_COLLAPSED = 4;

    /**
     * The bottom sheet is hidden.
     */
    public static final int STATE_HIDDEN = 5;
    private BottomSheetBehavior mBehavior;
    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback
            = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            System.out.println(newState);
            //禁止拖拽，
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
              //  dismiss();
                //设置为收缩状态
                //mBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
           if (slideOffset < 0.01){
               //dismiss();
           }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog dialog = new BottomSheetDialog(getContext(),R.style.ArticleReplyFragmentAnim);
        View view = View.inflate(getContext(), R.layout.bottom_dialog_article_reply, null);
        TextView content = view.findViewById(R.id.content);
        content.setLayoutParams(new ConstraintLayout.LayoutParams((int) (UiUtils.getScreenWidth(getContext())), (int) (UiUtils.getScreenHeight(getContext()))));
        dialog.setContentView(view);
        dialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet)
                .setBackgroundColor(getResources().getColor(android.R.color.transparent));
        mBehavior = BottomSheetBehavior.from((View) view.getParent());
        mBehavior.setBottomSheetCallback(mBottomSheetBehaviorCallback);
        return dialog;
    }
    @Override
    public void onStart()    {
        super.onStart();
        //默认全屏展开
        mBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        //mBehavior.setSkipCollapsed(true);
       // mBehavior.setPeekHeight(0);
    }
    public void doclick(View v) {
        //点击任意布局关闭
        mBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //去掉父布局的背景
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            if (parent != null) {
                parent.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

}
