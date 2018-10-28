package com.kx.todaynews.adapter.category;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.kx.todaynews.R;
import com.kx.todaynews.bean.ArticleCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/10/27.
 */
public class CategoryExpandAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    // 我的频道 标题部分
    public static final int TYPE_MY_CHANNEL_HEADER = 0;
    // 我的频道
    public static final int TYPE_MY = 1;
    // 其他频道 标题部分
    public static final int TYPE_OTHER_CHANNEL_HEADER = 2;
    // 其他频道
    public static final int TYPE_OTHER = 3;

    private List<ArticleCategory.DataBeanX.DataBean> mMyChannelItems = new ArrayList<>();
    private List<ArticleCategory.DataBeanX.DataBean> mOtherChannelItems = new ArrayList<>();
    private LayoutInflater mLayoutInflater ;
    private boolean isEditMode = false;


    public CategoryExpandAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setCategoryData(List<ArticleCategory.DataBeanX.DataBean> myData, List<ArticleCategory.DataBeanX.DataBean> otherData) {
        this.mMyChannelItems = myData;
        this.mOtherChannelItems = otherData;
    }

    public List<ArticleCategory.DataBeanX.DataBean> getMyChannelItems() {
        return mMyChannelItems;
    }

    @Override
    public int getItemCount() {
        // 我的频道  标题 + 我的频道.size + 其他频道 标题 + 其他频道.size
        return mMyChannelItems.size() + mOtherChannelItems.size() + 2;
    }
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {    // 我的频道 标题部分
            return TYPE_MY_CHANNEL_HEADER;
        } else if (position == mMyChannelItems.size() + 1) {    // 其他频道 标题部分
            return TYPE_OTHER_CHANNEL_HEADER;
        } else if (position > 0 && position < mMyChannelItems.size() + 1) {
            return TYPE_MY;
        } else {
            return TYPE_OTHER;
        }
    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder =null;
        View view = null;
        if (viewType == TYPE_MY_CHANNEL_HEADER){
            view = mLayoutInflater.inflate(R.layout.item_my_channel_header, parent, false);
            TextView tv_btn_edit = view.findViewById(R.id.tv_btn_edit);
            view.findViewById(R.id.tv_btn_edit).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!isEditMode) {
                        setEditMode((RecyclerView) parent);
                        tv_btn_edit.setText(R.string.finish);
                    } else {
                        setEditMode((RecyclerView) parent);
                        tv_btn_edit.setText(R.string.edit);
                    }
                }
            });
            baseViewHolder = new BaseViewHolder(view);
        }else if (viewType == TYPE_OTHER_CHANNEL_HEADER){
            view = mLayoutInflater.inflate(R.layout.item_other_channel_header, parent, false);
            baseViewHolder = new BaseViewHolder(view);
        }else if (viewType == TYPE_MY){
            view = mLayoutInflater.inflate(R.layout.item_my, parent, false);
            baseViewHolder = new BaseViewHolder(view);
        }else if (viewType == TYPE_OTHER){
            view = mLayoutInflater.inflate(R.layout.item_other, parent, false);
            baseViewHolder = new BaseViewHolder(view);
            TextView tv = view.findViewById(R.id.tv);
            RecyclerView recyclerView = ((RecyclerView) parent);
            ViewGroup viewParent = (ViewGroup) recyclerView.getParent();
            RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
            // 点击其他频道item.
            BaseViewHolder finalBaseViewHolder = baseViewHolder;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RelativeLayout parent = (RelativeLayout) tv.getParent();
                    final PathMeasure mPathMeasure;
                    final float[] mCurrentPosition = new float[2];
                    int parentLoc[] = new int[2];
                    parent.getLocationInWindow(parentLoc);
                    int startLoc[] = new int[2];
                    view.getLocationInWindow(startLoc);

                    final View startView = view;
                    if (startView.getParent()!=null){
                        RelativeLayout parent1 = (RelativeLayout) startView.getParent();
                        parent1.removeView(startView);
                    }
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(view.getWidth(), view.getHeight());
                    viewParent.addView(startView, params);

                    final View endView;
                    float toX, toY;
                    int endLoc[] = new int[2];
                    //进行判断
                    int i = mMyChannelItems.size();
                    //  我的频道暂无数据
                    if (i == 0) {
                        toX = view.getWidth();
                        toY = view.getHeight();
                    // 我的频道数据为4的整数，就移动到下一行。
                    } else if (i % 4 == 0) {
                        endView = manager.findViewByPosition(i-3);
                        endView.getLocationInWindow(endLoc);
                        toX = endLoc[0] ;
                        toY = endLoc[1] + view.getHeight();
                    } else {
                        // 添加在后面
                        endView = manager.findViewByPosition(i);
                        endView.getLocationInWindow(endLoc);
                        toX = endLoc[0] + view.getWidth() - parentLoc[0];
                        toY = endLoc[1] ;
                    }

                    float startX = startLoc[0] - parentLoc[0];
                    float startY = startLoc[1];

                    Path path = new Path();
                    path.moveTo(startX, startY);
                    path.lineTo(toX, toY);
                    mPathMeasure = new PathMeasure(path, false);
                    //属性动画实现
                    ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
                    valueAnimator.setDuration(500);
                    // 匀速插值器
                    valueAnimator.setInterpolator(new LinearInterpolator());
                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float value = (Float) animation.getAnimatedValue();
                            // 获取当前点坐标封装到mCurrentPosition
                            mPathMeasure.getPosTan(value, mCurrentPosition, null);
                            startView.setTranslationX(mCurrentPosition[0]);
                            startView.setTranslationY(mCurrentPosition[1]);
                        }
                    });
                    valueAnimator.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            viewParent.removeView(startView);
                            int position = finalBaseViewHolder.getAdapterPosition();

                            int startPosition = position - mMyChannelItems.size() - 2;

                            ArticleCategory.DataBeanX.DataBean item = mOtherChannelItems.get(startPosition);
                            mOtherChannelItems.remove(startPosition);
                            mMyChannelItems.add(item);
                            notifyItemMoved(position, mMyChannelItems.size() - 1 + 1);
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }
                        @Override
                        public void onAnimationRepeat(Animator animation) {
                        }
                    });
                    valueAnimator.start();
                }
            });

        }
        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (holder.getItemViewType()==TYPE_MY_CHANNEL_HEADER){
//            holder.setOnClickListener(R.id.tv_btn_edit, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (!isEditMode) {
//                        setEditMode(holder.get);
//                        holder.setText(R.id.tv_btn_edit,R.string.finish);
//                    } else {
//                        setEditMode((RecyclerView) parent);
//                        holder.setText(R.id.tv_btn_edit,R.string.edit);
//                    }
//                }
//            });
        }else if (holder.getItemViewType()== TYPE_MY  ) {
             ArticleCategory.DataBeanX.DataBean dataBean = mMyChannelItems.get(position-1);
             holder.setText(R.id.tv,dataBean.getName());
         }else if (holder.getItemViewType()== TYPE_OTHER  ) {
             ArticleCategory.DataBeanX.DataBean dataBean = mOtherChannelItems.get(position-(mMyChannelItems.size()+2));
             holder.setText(R.id.tv,dataBean.getName());
         }
    }

    /**
     * 开启编辑模式
     *
     * @param parent
     */
    private void setEditMode(RecyclerView parent) {
        isEditMode = !isEditMode;
        int visibleChildCount = parent.getChildCount();
        for (int i = 0; i < visibleChildCount; i++) {
            View view = parent.getChildAt(i);
            ImageView imgEdit = (ImageView) view.findViewById(R.id.img_edit);
            if (imgEdit != null) {
                imgEdit.setVisibility(View.VISIBLE);
            }
        }
    }
}
